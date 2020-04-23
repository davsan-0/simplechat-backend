package com.davsan.simplechat.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents the last read Message that a User has read from a Chat
 *
 * @author David Sandström
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "last_read")
public class LastRead implements Serializable {

    @EmbeddedId
    private LastReadIdentity id;

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private Message message;

    @NotNull
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedAt;

    public LastRead() {
    }

    public LastRead(LastReadIdentity id) {
        this.id = id;
    }

    public LastReadIdentity getId() {
        return id;
    }

    public void setId(LastReadIdentity id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
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
}

