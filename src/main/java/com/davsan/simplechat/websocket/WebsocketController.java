package com.davsan.simplechat.websocket;

import com.davsan.simplechat.dto.MessageDTO;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

import java.util.UUID;

@Controller
public class WebsocketController {

   // @MessageMapping("/chats")
    //@SendTo("/ws/topic/chats")
    @MessageMapping("/chats/{chatId}/message")
    @SendTo("/ws/topic/chats/{chatId}")
    public WebsocketMessage receivedMessage(WebsocketMessage message, @DestinationVariable UUID chatId) throws Exception {
        System.out.println(message.getUserId().toString());
        System.out.println(message.getText());
        return message;
    }

}
