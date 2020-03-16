package com.davsan.simplechat.dto;

import com.davsan.simplechat.model.Chat;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.model.User;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class maps models to DTOs and vice-versa.
 *
 * @author David Sandström
 */
public class Mapper {


    public static MessageDTO MessageToDTO(Message message) {
        if (message == null) return null;

        return MessageDTO.builder()
                .id(message.getId())
                .author(UserToDTOSimple(message.getAuthor()))
                .chatId(message.getChat().getId())
                .text(message.getText())
                .createdAt(message.getCreatedAt())
                .modifiedAt(message.getModifiedAt())
                .build();
    }

    public static Message DTOToMessage(MessageDTO dto) {
        User user = new User();
        user.setId(dto.getAuthor().getId());

        Chat chat = new Chat();
        chat.setId(dto.getChatId());

        return Message.builder()
                .id(dto.getId())
                .author(user)
                .chat(chat)
                .text(dto.getText())
                .createdAt(dto.getCreatedAt())
                .modifiedAt(dto.getModifiedAt())
                .build();

        //if (dto.getId() != null) message.setId(dto.getId());
    }

    // TODO: Implement
    public static UserDTO UserToDTO(User user) {
        return new UserDTO();
    }

    public static UserDTO UserToDTOSimple(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static ChatDTO ChatToDTO(Chat chat) {
        ChatDTO dto = new ChatDTO();

        dto.setId(chat.getId());
        dto.setName(chat.getName());
        dto.setParticipants(chat.getParticipants().stream().map(Mapper::UserToDTOSimple).collect(Collectors.toSet()));
        dto.setCreatedAt(chat.getCreatedAt());
        dto.setModifiedAt(chat.getModifiedAt());

        return dto;
    }

    public static ChatDTO ChatToDTO(Chat chat, Message latestMessage) {
        ChatDTO dto = ChatToDTO(chat);

        MessageDTO latestMessageDTO = MessageToDTO(latestMessage);
        dto.setLatestMessage(latestMessageDTO);

        return dto;
    }
}
