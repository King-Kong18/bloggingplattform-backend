package ch.bbw.bloggingplattform.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.bbw.bloggingplattform.blogs.Blog;
import jakarta.transaction.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:5173")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<BlogUser>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userRepository.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<BlogUser> getUser(@PathVariable long id) {
        Optional<BlogUser> user = userRepository.findById(id);
        return user.map(value -> ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        Optional<BlogUser> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            BlogUser user = optionalUser.get();

            for (Blog blog : user.getLikedBlogs()) {
                blog.getLikedByUsers().remove(user);
                blog.decrementLikes();
            }
            user.getLikedBlogs().clear();
            userRepository.delete(user);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody BlogUser newUser) {
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            java.util.Map.of("message", "Benutzername existiert bereits."));
        }

        newUser.setPassword(hashPassword(newUser.getPassword()));

        BlogUser savedUser = userRepository.save(newUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedUser);

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody BlogUser newUser) {
        Optional<BlogUser> currentUserOpt = userRepository.findById(id);

        if (currentUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User nicht gefunden");
        }

        BlogUser currentUser = currentUserOpt.get();

        // Prüfen, ob der neue Benutzername bereits von einem anderen Benutzer verwendet
        // wird
        Optional<BlogUser> userWithSameName = userRepository.findByUsername(newUser.getUsername());

        if (userWithSameName.isPresent() && !userWithSameName.get().getId().equals(currentUser.getId())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(java.util.Map.of("message", "Benutzername ist bereits vergeben."));
        }

        // Benutzername ist gültig
        currentUser.setUsername(newUser.getUsername());
        currentUser.setPassword(hashPassword(newUser.getPassword()));

        BlogUser updatedUser = userRepository.save(currentUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody BlogUser loginData) {
        Optional<BlogUser> userOpt = userRepository.findByUsername(loginData.getUsername());

        if (userOpt.isPresent()) {
            BlogUser user = userOpt.get();

            String hashedInput = hashPassword(loginData.getPassword());

            if (user.getPassword().equals(hashedInput)) {
                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fehlgeschlagen");
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 nicht verfügbar", e);
        }
    }

}
