package com.davsan.simplechat.model;

import com.davsan.simplechat.utils.AttributeEncryptor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "message")
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @NotNull
    private UUID id;

    @ManyToOne
    @JoinColumn(name="author_id")
    @NotNull
    @JsonIgnoreProperties({"name", "chats", "createdAt", "modifiedAt"})
    private User author;

    @ManyToOne
    @JoinColumn(name="chat_id")
    @NotNull
    @JsonIgnoreProperties({"participants", "createdAt", "modifiedAt"})
    private Chat chat;

    @Column(length = 255)
    @NotNull
    @Convert(converter = AttributeEncryptor.class)
    private String text;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Message() {
    }

    public Message(@NotNull UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class MessageBuilder {
        private UUID id;
        private User author;
        private Chat chat;
        private String text;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public MessageBuilder() {
        }

        public MessageBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public MessageBuilder author(User author) {
            this.author = author;
            return this;
        }

        public MessageBuilder chat(Chat chat) {
            this.chat = chat;
            return this;
        }

        public MessageBuilder text(String text) {
            this.text = text;
            return this;
        }

        public MessageBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MessageBuilder modifiedAt(LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public Message build() {
            Message message = new Message();

            message.setId(this.id);
            message.setAuthor(this.author);
            message.setChat(this.chat);
            message.setText(this.text);
            message.setCreatedAt(this.createdAt);
            message.setModifiedAt(this.modifiedAt);

            return message;
        }
    }
}
