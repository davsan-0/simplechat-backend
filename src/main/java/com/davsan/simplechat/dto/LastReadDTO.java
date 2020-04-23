package com.davsan.simplechat.dto;

import com.davsan.simplechat.model.LastReadIdentity;
import com.davsan.simplechat.model.Message;

import java.util.UUID;

public class LastReadDTO {
    private UUID userId;
    private UUID chatId;
    private UUID messageId;

    public LastReadDTO() {
    }

    public LastReadDTO(UUID userId, UUID chatId, UUID messageId) {
        this.userId = userId;
        this.chatId = chatId;
        this.messageId = messageId;
    }

    public LastReadDTO(LastReadIdentity id, Message message) {
        this.userId = id.getUserId().getId();
        this.chatId = id.getChatId().getId();
        this.messageId = message.getId();
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }
}
