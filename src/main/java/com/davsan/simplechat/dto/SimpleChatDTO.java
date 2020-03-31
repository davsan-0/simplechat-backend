package com.davsan.simplechat.dto;

import java.util.Set;
import java.util.UUID;

/**
 * This is a simplified version of the ChatDTO that only contains name and participants
 *
 * @author David Sandström
 */
public class SimpleChatDTO {
    private String name;
    private Set<UUID> participants;

    public SimpleChatDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UUID> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UUID> participants) {
        this.participants = participants;
    }
}
