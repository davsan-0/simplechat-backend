package com.davsan.simplechat.security;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ApiTokenWrapper {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType = "Bearer";

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_at")
    private Date expiresAt;

    public ApiTokenWrapper() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
