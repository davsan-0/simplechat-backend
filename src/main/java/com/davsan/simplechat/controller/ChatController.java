package com.davsan.simplechat.controller;

import com.davsan.simplechat.dto.Mapper;
import com.davsan.simplechat.dto.MessageDTO;
import com.davsan.simplechat.model.Chat;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.model.User;
import com.davsan.simplechat.service.ChatService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("chats")
public class ChatController {

    @Autowired
    ChatService chatService;

    @GetMapping
    public List<Chat> getChats() {
        return chatService.findAll();
    }

    @GetMapping("{id}")
    public Chat getChatById(@PathVariable UUID id) {
        return chatService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Chat createChat(@RequestBody Chat chat)
    {
        Preconditions.checkNotNull(chat);
        return chatService.saveChat(chat);
    }

    @PostMapping("{chat_id}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public void postMessageToChat(@PathVariable("chat_id") UUID chatId, @RequestBody MessageDTO message) {
        message.setChatId(chatId);
        Message entity = Mapper.DTOToMessage(message);
        chatService.postMessageToChat(entity);
    }

    @PostMapping("{chat_id}/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUsersToChat(@PathVariable("chat_id") UUID chatId, @RequestBody List<UUID> userIds) {
        chatService.addUsersToChat(chatId, userIds);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChat(@PathVariable UUID id) {
        chatService.deleteChatById(id);
    }



   /* @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Chat updateChat(@RequestBody Chat chat) {
        Preconditions.checkNotNull(chat);

        return chatService.updateChatWithId(chat.getId(), chat);
    }*/
}
