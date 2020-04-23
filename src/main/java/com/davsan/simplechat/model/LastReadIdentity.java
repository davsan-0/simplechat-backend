package com.davsan.simplechat.model;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class LastReadIdentity implements Serializable {

    @NotNull
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @NotNull
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chatId;

    public LastReadIdentity() {
    }

    public LastReadIdentity(@NotNull User userId, @NotNull Chat chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Chat getChatId() {
        return chatId;
    }

    public void setChatId(Chat chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastReadIdentity that = (LastReadIdentity) o;
        return userId.equals(that.userId) &&
                chatId.equals(that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, chatId);
    }
}
