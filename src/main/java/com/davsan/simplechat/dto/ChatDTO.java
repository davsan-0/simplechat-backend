package com.davsan.simplechat.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * This is the Data Transfer Object class of the model class Chat which is used to separate what is returned to a client and what is stored in the database
 *
 * @author David Sandström
 */
public class ChatDTO {
    private UUID id;
    private String name;
    private Set<UserDTO> participants;
    private MessageDTO latestMessage;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ChatDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UserDTO> participants) {
        this.participants = participants;
    }

    public MessageDTO getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(MessageDTO latestMessage) {
        this.latestMessage = latestMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatDTO chatDTO = (ChatDTO) o;
        return id.equals(chatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
