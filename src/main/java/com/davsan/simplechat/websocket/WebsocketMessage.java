package com.davsan.simplechat.websocket;

import java.util.UUID;

public class WebsocketMessage {

    private UUID userId;
    private String text;

    public WebsocketMessage() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
