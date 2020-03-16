package com.davsan.simplechat.model;

import com.davsan.simplechat.dto.ChatDTO;
import com.davsan.simplechat.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name="app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false)
    @NotNull
    private UUID id;

    @Column
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    @JsonIgnoreProperties({"participants", "createdAt", "modifiedAt"})
    private Set<Chat> chats = new HashSet<>();

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public User() {
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

    /**
     * Retrieves chats that this User is participating in
     * @return a set of all chats
     */
    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    /**
     * Adds this User as a participant of a Chat
     * @param chat the Chat to add this User to
     */
    public void addChat(Chat chat) {
        chats.add(chat);
        chat.getParticipants().add(this);
    }

    /**
     * Removes this User as a participant of a Chat
     * @param chat the Chat to remove this User from
     */
    public void removeChat(Chat chat) {
        chats.remove(chat);
        chat.getParticipants().remove(this);
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class UserBuilder {
        private UUID id;
        private String name;
        private Set<Chat> chats;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public UserBuilder() {
        }

        public UserBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder chats(Set<Chat> chats) {
            this.chats = chats;
            return this;
        }

        public UserBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder modifiedAt(LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public User build() {
            User user = new User();

            user.setId(this.id);
            user.setName(this.name);
            user.setChats(this.chats);
            user.setCreatedAt(this.createdAt);
            user.setModifiedAt(this.modifiedAt);

            return user;
        }
    }
}
