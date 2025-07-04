package ch.bbw.bloggingplattform.chat;

import java.util.List;

import ch.bbw.bloggingplattform.user.BlogUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<BlogUser> participants;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
}
