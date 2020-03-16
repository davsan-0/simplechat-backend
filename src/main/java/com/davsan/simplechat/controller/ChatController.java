package com.davsan.simplechat.controller;

import com.davsan.simplechat.dto.ChatDTO;
import com.davsan.simplechat.dto.Mapper;
import com.davsan.simplechat.dto.MessageDTO;
import com.davsan.simplechat.model.Chat;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ChatDTO getChatById(@PathVariable UUID id) {
        return chatService.findByIdAndReturnDTO(id);
    }

    @GetMapping("{chat_id}/messages")
    public List<MessageDTO> getMessagesFromChatSinceDate(@PathVariable("chat_id") UUID chatId, @RequestParam(required = false) String since) {
        if (since == null) {
            return chatService.getMessagesFromChat(chatId).stream().map(Mapper::MessageToDTO).collect(Collectors.toList());
        }

        LocalDateTime ldt  = LocalDateTime.parse(since, DateTimeFormatter.ISO_DATE_TIME);
        return chatService.getMessagesFromChatAfterDate(chatId, ldt).stream().map(Mapper::MessageToDTO).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createChat(@RequestBody List<UUID> userIds)
    {
        chatService.createChat(userIds);
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
