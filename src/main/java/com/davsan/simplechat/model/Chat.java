package com.davsan.simplechat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "chats")
    @JsonIgnoreProperties({"chats", "createdAt", "modifiedAt"})
    private Set<User> participants = new HashSet<>();

    @Column(name = "created_date", updatable = false)
    @NotNull
    @CreatedDate
    private Date createdAt;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedAt;


    public Chat() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
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

    public void addUser(User user) {
        participants.add(user);
        user.getChats().add(this);
    }

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
