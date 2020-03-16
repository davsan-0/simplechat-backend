package com.davsan.simplechat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false)
    @NotNull
    private UUID id;

    @Column(updatable = true, nullable = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "chats")
    @JsonIgnoreProperties({"chats", "createdAt", "modifiedAt"})
    private Set<User> participants = new HashSet<>();

    @Column(name = "created_date", updatable = false)
    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Chat() {
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

    /**
     * Retrieves all the users that are part of this chat
     * @return a set of all users in the chat
     */
    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
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

   /* public Message getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(Message latestMessage) {
        this.latestMessage = latestMessage;
    }*/

    /**
     * Adds a user as a participant to this chat
     * @param user the user to add to this chat
     */
    public void addUser(User user) {
        participants.add(user);
        user.getChats().add(this);
    }

    /**
     * Removes a user from this chat
     * @param user the user to remove from this chat
     */
    public void removeUser(User user) {
        participants.remove(user);
        user.getChats().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id.equals(chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
