package com.davsan.simplechat.dto;

import com.davsan.simplechat.model.Chat;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.model.User;

import java.util.stream.Collectors;

public class Mapper {


    public static MessageDTO MessageToDTO(Message message) {
        MessageDTO dto = new MessageDTO();

        dto.setId(message.getId());
        dto.setAuthor(UserToDTOSimple(message.getAuthor()));
        if (message.getChat() != null) dto.setChatId(message.getChat().getId());
        dto.setText(message.getText());
        dto.setCreatedAt(message.getCreatedAt());
        dto.setModifiedAt(message.getModifiedAt());

        return dto;
    }

    public static Message DTOToMessage(MessageDTO dto) {
        Message message = new Message();

        if (dto.getId() != null) message.setId(dto.getId());

        User user = new User();
        user.setId(dto.getAuthor().getId());
        message.setAuthor(user);

        Chat chat = new Chat();
        chat.setId(dto.getChatId());
        message.setChat(chat);

        message.setText(dto.getText());
        message.setCreatedAt(dto.getCreatedAt());
        message.setModifiedAt(dto.getModifiedAt());

        return message;
    }

    // TODO: Implement
    public static UserDTO UserToDTO(User user) {
        return new UserDTO();
    }

    public static UserDTO UserToDTOSimple(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());

        return dto;
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

        latestMessage.setChat(null); // Don't need to send the chat id back
        dto.setLatestMessage(MessageToDTO(latestMessage));

        return dto;
    }
}
