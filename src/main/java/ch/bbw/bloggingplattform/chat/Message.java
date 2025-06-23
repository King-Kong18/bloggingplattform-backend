package ch.bbw.bloggingplattform.chat;

import java.time.LocalDateTime;

import ch.bbw.bloggingplattform.user.BlogUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    private BlogUser sender;

    @ManyToOne
    private BlogUser recipient;

    private LocalDateTime timestamp;
}
