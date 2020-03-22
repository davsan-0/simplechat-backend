package com.davsan.simplechat.security;

import com.davsan.simplechat.model.Provider;

public class ProviderToken {

    private String token;
    private Provider provider;

    public ProviderToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
