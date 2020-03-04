package com.davsan.simplechat.repository;

import com.davsan.simplechat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {


}
