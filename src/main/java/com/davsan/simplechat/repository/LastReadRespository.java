package com.davsan.simplechat.repository;

import com.davsan.simplechat.dto.LastReadDTO;
import com.davsan.simplechat.model.Chat;
import com.davsan.simplechat.dto.LastReadDTO;
import com.davsan.simplechat.model.LastRead;
import com.davsan.simplechat.model.LastReadIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LastReadRespository extends JpaRepository<LastRead, LastReadIdentity> {

    @Query(value = "SELECT new com.davsan.simplechat.dto.LastReadDTO(lr.id, lr.message) FROM LastRead lr WHERE lr.id.chatId.id = ?1")
    List<LastReadDTO> findAllLastReadInChat(UUID chatId);
}
