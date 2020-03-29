package com.davsan.simplechat.websocket;

import com.davsan.simplechat.dto.Mapper;
import com.davsan.simplechat.dto.MessageDTO;
import com.davsan.simplechat.dto.UserDTO;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class WebsocketController {

    @Autowired
    ChatService chatService;

   // @MessageMapping("/chats")
    //@SendTo("/ws/topic/chats")
    @MessageMapping("/chats/{chatId}/message")
    @SendTo("/ws/topic/chats/{chatId}")
    public MessageDTO receivedMessage(WebsocketMessage message, @DestinationVariable UUID chatId) throws Exception {
        UserDTO user = new UserDTO(message.getUserId());

        MessageDTO messageDTO = MessageDTO.builder()
                .author(user)
                .text(message.getText())
                .chatId(chatId)
                .build();

        Message newMessage = chatService.postMessageToChat(Mapper.DTOToMessage(messageDTO));

        MessageDTO returnMessage = Mapper.MessageToDTO(newMessage);
        returnMessage.getAuthor().setName(message.getName());
        return returnMessage;
    }

}
