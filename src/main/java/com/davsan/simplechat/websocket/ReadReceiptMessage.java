package com.davsan.simplechat.websocket;

import java.util.UUID;

public class ReadReceiptMessage {

    private UUID userId;
    private UUID messageId;

    public ReadReceiptMessage() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }
}
