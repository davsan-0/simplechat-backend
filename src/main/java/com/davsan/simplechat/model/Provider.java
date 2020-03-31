package com.davsan.simplechat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The accepted 3rd party providers that can be used to authenticate to receive an access token
 *
 * @author David Sandström
 */
public enum Provider {
    GOOGLE("google");

    private String key;

    Provider(String key) {
        this.key = key;
    }

    @JsonCreator
    public static Provider fromString(String key) {
        return key == null
                ? null
                : Provider.valueOf(key.toUpperCase());
    }

    @JsonValue
    public String getKey() {
        return key;
    }
}
