package com.davsan.simplechat.service;

import com.davsan.simplechat.dto.ChatDTO;
import com.davsan.simplechat.dto.Mapper;
import com.davsan.simplechat.dto.SimpleChatDTO;
import com.davsan.simplechat.error.ResourceNotFoundException;
import com.davsan.simplechat.model.Chat;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.model.User;
import com.davsan.simplechat.repository.ChatRepository;
import com.davsan.simplechat.repository.MessageRepository;
import com.davsan.simplechat.utils.OnlineUsersMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;

    @Autowired
    OnlineUsersMap onlineUsersMap;

    public void createChat(SimpleChatDTO chatDTO) {
        Chat chat = new Chat();
        chat.setName(chatDTO.getName());

        for (UUID userId : chatDTO.getParticipants()) {
            User user = userService.findById(userId);
            chat.addUser(user);
        }

        chatRepository.saveAndFlush(chat);
    }

    public Chat saveChat(Chat chat) {
        try {
            Chat savedChat = chatRepository.saveAndFlush(chat);
            return savedChat;
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect request received.");
        }
    }

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    public Chat findById(UUID id) {
        return chatRepository.findById(id).orElseThrow(() -> { throw new ResourceNotFoundException("Chat " + id.toString() + " not found"); });
    }

    public ChatDTO findByIdAndReturnDTO(UUID id) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> { throw new ResourceNotFoundException("Chat " + id.toString() + " not found"); });
        Message latestMessage = messageRepository.findFirst1ByChat_idOrderByCreatedAtDesc(id);

        return Mapper.ChatToDTO(chat, latestMessage);
    }

    public List<Message> getMessagesFromChat(UUID chatId, Sort.Direction sortDir) {
        return messageRepository.findByChat_id(chatId, Sort.by(sortDir, "createdAt"));
    }

    public List<Message> getMessagesFromChatAfterDate(UUID chatId, LocalDateTime date, Sort.Direction sortDir) {
        return messageRepository.findAllMessagesAfterDate(chatId, date, sortDir).orElseThrow(() -> { throw new ResourceNotFoundException("Chat " + chatId.toString() + " not found"); });
    }

    public List<ChatDTO> getChatsContainingUser(UUID userId) {
        List<Chat> chatList = chatRepository.findByParticipants_IdEquals(userId);
        List<ChatDTO> dtoList = new ArrayList<>();

        for (Chat chat : chatList) {
            Message latestMessage = messageRepository.findFirst1ByChat_idOrderByCreatedAtDesc(chat.getId());
            ChatDTO dto = Mapper.ChatToDTO(chat, latestMessage);
            dto.getParticipants().forEach(userDTO -> {
                if (onlineUsersMap.containsKey(userDTO.getId())) userDTO.setOnline(true);
            });

            dtoList.add(dto);
        }

        return dtoList;
    }

    public Message postMessageToChat(Message message) {
        return messageRepository.saveAndFlush(message);
    }

    public void deleteChatById(UUID id) {
        chatRepository.deleteById(id);
    }

    public void addUsersToChat(UUID chatId, List<UUID> userIds) {
        Chat chat = findById(chatId);

        for (UUID userId : userIds) {
            User user = userService.findById(userId);
            chat.addUser(user);
        }

        chatRepository.saveAndFlush(chat);
    }

    /*public Chat updateChatWithId(UUID id, Chat chat) {
        try {
            Chat chatToUpdate = chatRepository.findById(id).orElseThrow(() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"); });

            if (chat.getName() != null) chatToUpdate.setName(chat.getName());

            return chatRepository.saveAndFlush(chatToUpdate);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect request received.");
        }
    }*/
}
