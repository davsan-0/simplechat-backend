package com.davsan.simplechat.dto;

import com.davsan.simplechat.model.Message;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * This is the Data Transfer Object class of the model class Message which is used to separate what is returned to a client and what is stored in the database
 *
 * @author David Sandström
 */
public class MessageDTO {
    private UUID id;
    private UserDTO author;
    private UUID chatId;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MessageDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public static MessageDTOBuilder builder() {
        return new MessageDTOBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO that = (MessageDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class MessageDTOBuilder {
        private UUID id;
        private UserDTO author;
        private UUID chatId;
        private String text;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public MessageDTOBuilder() {
        }

        public MessageDTOBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public MessageDTOBuilder author(UserDTO author) {
            this.author = author;
            return this;
        }

        public MessageDTOBuilder chatId(UUID chatId) {
            this.chatId = chatId;
            return this;
        }

        public MessageDTOBuilder text(String text) {
            this.text = text;
            return this;
        }

        public MessageDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MessageDTOBuilder modifiedAt(LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public MessageDTO build() {
            MessageDTO dto = new MessageDTO();

            dto.setId(this.id);
            dto.setAuthor(this.author);
            dto.setChatId(this.chatId);
            dto.setText(this.text);
            dto.setCreatedAt(this.createdAt);
            dto.setModifiedAt(this.modifiedAt);

            return dto;
        }
    }
}
