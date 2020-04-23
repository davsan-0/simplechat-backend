package com.davsan.simplechat.repository;

import com.davsan.simplechat.model.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query(value = "SELECT * FROM message WHERE chat_id = ?1 AND created_date > ?2 ORDER BY created_date ?3", nativeQuery = true)
    Optional<List<Message>> findAllMessagesInChatAfterDate(UUID chatId, LocalDateTime date, Sort.Direction sortDir);

    @Query(value = "SELECT * FROM message WHERE chat_id = ?1 AND created_date > (SELECT created_date FROM message WHERE id = ?2) GROUP BY chat_id ORDER BY created_date", nativeQuery = true)
    Optional<List<Message>> findAllMessagesInChatAfterMessage(UUID chatId, UUID messageId);

    @Query(value= "SELECT COUNT(*) FROM (SELECT * FROM message WHERE chat_id = ?1 AND author_id != ?2 AND created_date > (SELECT created_date FROM message WHERE id = ?3) ORDER BY created_date) as sq", nativeQuery = true)
    int countAllMessagesInChatAfterMessageNotIncludingUser(UUID chatId, UUID userId, UUID messageId);

    @Query(value= "SELECT COUNT(*) FROM (SELECT * FROM message WHERE chat_id = ?1 AND author_id != ?2 ORDER BY created_date) as sq", nativeQuery = true)
    int countAllMessagesInChatNotIncludingUser(UUID chatId, UUID userId);

    List<Message> findByChat_id(UUID chatId, Sort sort);

    Message findFirst1ByChat_idOrderByCreatedAtDesc(UUID chatId);
}
