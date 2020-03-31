package com.davsan.simplechat.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
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

    @Id
    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne(cascade = {})
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private Message message;

    @NotNull
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedAt;

}

