package com.davsan.simplechat.websocket;

import java.util.UUID;

public class OnlineStatusMessage {

    private UUID userId;
    private boolean isOnline;

    public OnlineStatusMessage() {
    }

    public OnlineStatusMessage(UUID userId, boolean isOnline) {
        this.userId = userId;
        this.isOnline = isOnline;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
