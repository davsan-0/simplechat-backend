package com.davsan.simplechat.service;

import com.davsan.simplechat.model.User;
import com.davsan.simplechat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user) {
        try {
            User savedUser = userRepository.saveAndFlush(user);
            return savedUser;
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect request received.");
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); });
    }


    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    public User updateUserWithId(UUID id, User user) {
        try {
            User userToUpdate = userRepository.findById(id).orElseThrow(() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); });

            if (user.getName() != null) userToUpdate.setName(user.getName());

            return userRepository.saveAndFlush(userToUpdate);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect request received.");
        }
    }
}
