package ch.bbw.bloggingplattform.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(
        Long senderId, Long recipientId, Long recipientId2, Long senderId2);
}

