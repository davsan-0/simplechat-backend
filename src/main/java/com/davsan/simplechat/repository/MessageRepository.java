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
    Optional<List<Message>> findByChat_idAndCreatedAtGreaterThanOrderByCreatedAtDesc(UUID chatId, Date date);

    @Query(value = "SELECT * FROM message WHERE chat_id = ?1 AND created_date > ?2 ORDER BY created_date ?3", nativeQuery = true)
    Optional<List<Message>> findAllMessagesAfterDate(UUID chatId, LocalDateTime date, Sort.Direction sortDir);

    List<Message> findByChat_id(UUID chatId, Sort sort);

    Message findFirst1ByChat_idOrderByCreatedAtDesc(UUID chatId);
}
