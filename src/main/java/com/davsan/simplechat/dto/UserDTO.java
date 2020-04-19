package com.davsan.simplechat.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * This is the Data Transfer Object class of the model class User which is used to separate what is returned to a client and what is stored in the database
 *
 * @author David Sandström
 */
public class UserDTO {
    private UUID id;
    private String name;
    private String imageUrl;
    private Set<ChatDTO> chats;
    private boolean online;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserDTO() {
    }

    public UserDTO(UUID id) {
        this.id = id;
    }

    public UserDTO(String id) {
        this.id = UUID.fromString(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<ChatDTO> getChats() {
        return chats;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setChats(Set<ChatDTO> chats) {
        this.chats = chats;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class UserDTOBuilder {
        private UUID id;
        private String name;
        private String imageUrl;
        private Set<ChatDTO> chats;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public UserDTOBuilder() {
        }

        public UserDTOBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserDTOBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public UserDTOBuilder chats(Set<ChatDTO> chats) {
            this.chats = chats;
            return this;
        }

        public UserDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserDTOBuilder modifiedAt(LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public UserDTO build() {
            UserDTO dto = new UserDTO();

            dto.setId(this.id);
            dto.setName(this.name);
            dto.setImageUrl(this.imageUrl);
            dto.setChats(this.chats);
            dto.setCreatedAt(this.createdAt);
            dto.setModifiedAt(this.modifiedAt);

            return dto;
        }
    }
}
