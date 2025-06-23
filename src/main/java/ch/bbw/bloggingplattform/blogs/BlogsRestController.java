package ch.bbw.bloggingplattform.blogs;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.bbw.bloggingplattform.user.BlogUser;
import ch.bbw.bloggingplattform.user.UserRepository;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class BlogsRestController {

    private final BlogRepository blogsRepositoryDB;
    private final UserRepository userRepository;

    public BlogsRestController(BlogRepository blogsRepositoryDB, UserRepository userRepository) {
        this.blogsRepositoryDB = blogsRepositoryDB;
        this.userRepository = userRepository;
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> getBlogs() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body((List<Blog>) blogsRepositoryDB.findAll());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likeBlog(@PathVariable Long id, @RequestBody BlogUser userRequest) {
        Optional<Blog> optionalBlog = blogsRepositoryDB.findById(id);
        Optional<BlogUser> optionalUser = userRepository.findByUsername(userRequest.getUsername());

        if (optionalBlog.isPresent() && optionalUser.isPresent()) {
            Blog blog = optionalBlog.get();
            BlogUser user = optionalUser.get();

            if (!blog.getLikedByUsers().contains(user)) {
                blog.getLikedByUsers().add(user);
                blog.incrementLikes();
                blogsRepositoryDB.save(blog);
                return ResponseEntity.ok("Blog geliked!");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User hat bereits geliked");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog oder User nicht gefunden");
        }
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<String> unlikeBlog(@PathVariable Long id, @RequestBody BlogUser userRequest) {
        Optional<Blog> optionalBlog = blogsRepositoryDB.findById(id);
        Optional<BlogUser> optionalUser = userRepository.findByUsername(userRequest.getUsername());

        if (optionalBlog.isPresent() && optionalUser.isPresent()) {
            Blog blog = optionalBlog.get();
            BlogUser user = optionalUser.get();

            if (blog.getLikedByUsers().contains(user)) {
                blog.getLikedByUsers().remove(user);
                blog.decrementLikes();
                blogsRepositoryDB.save(blog);
                return ResponseEntity.ok("Like entfernt");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User hat diesen Blog nicht geliked");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog oder User nicht gefunden");
        }
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable long id) {
        Optional<Blog> blog = blogsRepositoryDB.findById(id);
        return blog.map(value -> ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable long id) {
        if (blogsRepositoryDB.findById(id).isPresent()) {
            blogsRepositoryDB.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/blogs")
    public ResponseEntity<Iterable<Blog>> deleteAllBlogs() {
        blogsRepositoryDB.deleteAll();
        return ResponseEntity.ok(blogsRepositoryDB.findAll());
    }

    @PostMapping("/blogs")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog newBlog) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(blogsRepositoryDB.save(newBlog));
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable long id, @RequestBody Blog newBlog) {
        Optional<Blog> currentBlog = blogsRepositoryDB.findById(id);

        return currentBlog
                .map(blog -> {
                    blog.setTitle(newBlog.getTitle());
                    blog.setContent(newBlog.getContent());
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(blogsRepositoryDB.save(blog));
                }).orElseGet(() -> {
                    newBlog.setId(id);
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(blogsRepositoryDB.save(newBlog));
                });
    }
}
