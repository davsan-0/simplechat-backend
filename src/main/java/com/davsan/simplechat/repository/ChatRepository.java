package com.davsan.simplechat.repository;

import com.davsan.simplechat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

   // @Query(value = "SELECT chat_id FROM user_chat WHERE ", nativeQuery = true)
    //List<Chat> getChatsContainingUser(UUID userId);

    List<Chat> findByParticipants_IdEquals(UUID userId);
}
