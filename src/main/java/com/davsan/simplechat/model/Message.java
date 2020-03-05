package com.davsan.simplechat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
    private String text;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @NotNull
    private Date createdAt;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedAt;

    public Message() {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
