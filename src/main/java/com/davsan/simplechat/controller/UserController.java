package com.davsan.simplechat.controller;

import com.davsan.simplechat.dto.ChatDTO;
import com.davsan.simplechat.dto.UserDTO;
import com.davsan.simplechat.model.User;
import com.davsan.simplechat.service.ChatService;
import com.davsan.simplechat.service.UserService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;

    @GetMapping
    public List<UserDTO> getUsers(@RequestParam(required = false) String search) {
        if (search == null) {
            return userService.findAll();
        }
        return userService.findByNameContaining(search);
    }

    @GetMapping("me")
    public UserDTO getMe() {
        UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByIdReturnDTO(id);
    }

    @GetMapping("me/chats")
    public List<ChatDTO> getMyChats() {
        UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return chatService.getChatsContainingUser(id);
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable UUID id) {
        return userService.findById(id);
    }


    @GetMapping("{id}/chats")
    public List<ChatDTO> getChatsWithUser(@PathVariable UUID id) {
        return chatService.getChatsContainingUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user)
    {
        Preconditions.checkNotNull(user);
        return userService.saveUser(user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user) {
        Preconditions.checkNotNull(user);
        return userService.updateUserWithId(user.getId(), user);
    }
}
