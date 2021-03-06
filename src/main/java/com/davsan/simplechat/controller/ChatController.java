package com.davsan.simplechat.controller;

import com.davsan.simplechat.dto.*;
import com.davsan.simplechat.model.Chat;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
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

    @GetMapping("{id}/messages")
    public List<MessageDTO> getMessagesFromChatSinceDate(@PathVariable("id") UUID chatId, @RequestParam(required = false) String since, @RequestParam(required = false) String sort) {
        Sort.Direction sortDir = Sort.Direction.fromOptionalString(sort).orElse(Sort.Direction.ASC);

        if (since == null) {
            return chatService.getMessagesFromChat(chatId, sortDir).stream().map(Mapper::MessageToDTO).collect(Collectors.toList());
        }

        LocalDateTime ldt  = LocalDateTime.parse(since, DateTimeFormatter.ISO_DATE_TIME);
        return chatService.getMessagesFromChatAfterDate(chatId, ldt, sortDir).stream().map(Mapper::MessageToDTO).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createChat(@RequestBody SimpleChatDTO chat) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chat.getParticipants().add(userId);
        chatService.createChat(chat);
    }

    @PostMapping("{id}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public void postMessageToChat(@PathVariable("id") UUID chatId, @RequestBody MessageDTO message) throws AccessDeniedException {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Chat chat = chatService.findById(chatId);
        boolean isParticipant = chat.getParticipants().stream().anyMatch(user -> user.getId().equals(userId));

        if (!isParticipant) {
            throw new AccessDeniedException("Access Denied.");
        }

        message.setAuthor(new UserDTO(userId));
        message.setChatId(chatId);
        Message entity = Mapper.DTOToMessage(message);
        chatService.postMessageToChat(entity);
    }

    @PostMapping("{id}/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUsersToChat(@PathVariable("id") UUID chatId, @RequestBody List<UUID> userIds) {
        chatService.addUsersToChat(chatId, userIds);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChat(@PathVariable UUID id) {
        chatService.deleteChatById(id);
    }

    @PutMapping("{id}/lastmessageread")
    @ResponseStatus(HttpStatus.OK)
    public void setLastMessageRead(@PathVariable("id") UUID chatId,  @RequestBody Map<String, UUID> messageid) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        chatService.setLastRead(userId, chatId, messageid.get("message_id"));
    }


   /* @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Chat updateChat(@RequestBody Chat chat) {
        Preconditions.checkNotNull(chat);

        return chatService.updateChatWithId(chat.getId(), chat);
    }*/
}
