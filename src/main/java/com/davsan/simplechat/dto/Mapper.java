package com.davsan.simplechat.dto;

import com.davsan.simplechat.model.Chat;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.model.User;

public class Mapper {


    public static MessageDTO MessageToDTO(Message message) {
        MessageDTO dto = new MessageDTO();

        dto.setId(message.getId());
        dto.setAuthor(message.getAuthor().getId());
        dto.setChatId(message.getChat().getId());
        dto.setText(message.getText());
        dto.setCreatedAt(message.getCreatedAt());
        dto.setModifiedAt(message.getModifiedAt());

        return dto;
    }

    public static Message DTOToMessage(MessageDTO dto) {
        Message message = new Message();

        if (dto.getId() != null) message.setId(dto.getId());

        User user = new User();
        user.setId(dto.getAuthor());
        message.setAuthor(user);

        Chat chat = new Chat();
        chat.setId(dto.getChatId());
        message.setChat(chat);

        message.setText(dto.getText());
        message.setCreatedAt(dto.getCreatedAt());
        message.setModifiedAt(dto.getModifiedAt());

        return message;
    }
}
