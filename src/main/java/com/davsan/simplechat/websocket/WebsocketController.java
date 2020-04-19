package com.davsan.simplechat.websocket;

import com.davsan.simplechat.dto.Mapper;
import com.davsan.simplechat.dto.MessageDTO;
import com.davsan.simplechat.dto.UserDTO;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.service.ChatService;
import com.davsan.simplechat.utils.OnlineUsersMap;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class WebsocketController {

    @Autowired
    ChatService chatService;

    @Autowired
    OnlineUsersMap onlineUsersMap;

    @Autowired
    private SimpMessagingTemplate webSocket;

    @MessageMapping("/chats/{chatId}/message")
    @SendTo("/ws/topic/chats/{chatId}/message")
    public MessageDTO receivedMessage(WebsocketMessage message, @DestinationVariable UUID chatId) throws Exception {
        UserDTO user = new UserDTO(message.getUserId());

        MessageDTO messageDTO = MessageDTO.builder()
                .author(user)
                .text(message.getText())
                .chatId(chatId)
                .build();

        Message newMessage = chatService.postMessageToChat(Mapper.DTOToMessage(messageDTO));

        MessageDTO returnMessage = Mapper.MessageToDTO(newMessage);
        return returnMessage;
    }

    @MessageMapping("/chats/{chatId}/readreceipt")
    @SendTo("/ws/topic/chats/{chatId}/readreceipt")
    public ReadReceiptMessage readReceipt(ReadReceiptMessage message) throws Exception {
        return message;
    }

    @MessageMapping("/chats/{chatId}/online")
    public void isOnline(String userId, @DestinationVariable UUID chatId) throws Exception {
        UUID uuid = UUID.fromString(userId);

        if (onlineUsersMap.containsKey(uuid)) {
            ArrayList chats = onlineUsersMap.get(uuid);
            if (!chats.contains(chatId)) {
                chats.add(chatId);
                OnlineStatusMessage onlineStatusMessage = new OnlineStatusMessage(uuid, true);
                webSocket.convertAndSend("/ws/topic/chats/" + chatId.toString() + "/online", onlineStatusMessage);
            }
        } else {
            ArrayList chats = new ArrayList<UUID>();
            chats.add(chatId);
            onlineUsersMap.put(uuid, chats);

            OnlineStatusMessage onlineStatusMessage = new OnlineStatusMessage(uuid, true);
            webSocket.convertAndSend("/ws/topic/chats/" + chatId.toString() + "/online", onlineStatusMessage);
        }


    }
}
