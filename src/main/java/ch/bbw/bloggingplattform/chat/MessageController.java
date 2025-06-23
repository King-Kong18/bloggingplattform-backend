package ch.bbw.bloggingplattform.chat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.bbw.bloggingplattform.user.BlogUser;
import ch.bbw.bloggingplattform.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:5173")
public class MessageController {

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto) {
        Optional<BlogUser> senderOpt = userRepo.findById(messageDto.getSenderId());
        Optional<BlogUser> recipientOpt = userRepo.findById(messageDto.getRecipientId());

        if (senderOpt.isEmpty() || recipientOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Ungültige Benutzer-ID");
        }

        Message message = new Message();
        message.setSender(senderOpt.get());
        message.setRecipient(recipientOpt.get());
        message.setContent(messageDto.getContent());
        message.setTimestamp(LocalDateTime.now());

        messageRepo.save(message);
        return ResponseEntity.ok().body("Nachricht gesendet");
    }

    @GetMapping
    public ResponseEntity<List<Message>> getChat(
            @RequestParam Long user1,
            @RequestParam Long user2) {
        List<Message> messages = messageRepo.findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(
                user1, user2, user1, user2);
        return ResponseEntity.ok(messages);
    }
}
