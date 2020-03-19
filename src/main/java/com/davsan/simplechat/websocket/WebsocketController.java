package com.davsan.simplechat.websocket;

import com.davsan.simplechat.dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebsocketController {

    @MessageMapping("/chats/message")
    @SendTo("/topic/chats")
    public MessageDTO receivedMessage(MessageDTO message) throws Exception {
        return message;
    }

}
