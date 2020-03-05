package com.davsan.simplechat.service;

import com.davsan.simplechat.dto.MessageDTO;
import com.davsan.simplechat.model.Chat;
import com.davsan.simplechat.model.Message;
import com.davsan.simplechat.model.User;
import com.davsan.simplechat.repository.ChatRepository;
import com.davsan.simplechat.repository.MessageRepository;
import com.davsan.simplechat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;

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
        return chatRepository.findById(id).orElseThrow(() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat " + id.toString() + " not found"); });
    }

    public void postMessageToChat(Message message) {
        messageRepository.saveAndFlush(message);
    }

    public void deleteChatById(UUID id) {
        chatRepository.deleteById(id);
    }

    public void addUsersToChat(UUID chatId, List<UUID> userIds) {
        Chat chat = findById(chatId);

        for (UUID userId : userIds) {
            User user = userRepository.findById(userId).orElseThrow(() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + userId.toString() + " not found"); });
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
