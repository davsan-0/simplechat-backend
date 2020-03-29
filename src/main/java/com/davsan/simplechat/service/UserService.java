package com.davsan.simplechat.service;

import com.davsan.simplechat.dto.Mapper;
import com.davsan.simplechat.dto.UserDTO;
import com.davsan.simplechat.error.ResourceNotFoundException;
import com.davsan.simplechat.model.Provider;
import com.davsan.simplechat.model.User;
import com.davsan.simplechat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(Mapper::UserToDTOSimple).collect(Collectors.toList());
    }

    public UserDTO findByIdReturnDTO(UUID id) {
        return Mapper.UserToDTO(findById(id));
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> { throw new ResourceNotFoundException("User " + id.toString() + " not found"); });
    }

    public User findByProviderId(String providerId, Provider provider) {
        Optional<User> user = userRepository.findByProviderIdAndProvider(providerId, provider);

        if (user.isPresent()) {
            return user.get();
        }

        return null;
    }

    public List<UserDTO> findByNameContaining(String search) {
        return userRepository.findByNameContainingIgnoreCase(search).stream().map(Mapper::UserToDTOSimple).collect(Collectors.toList());
    }

    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    public User updateUserWithId(UUID id, User user) {
        try {
            User userToUpdate = userRepository.findById(id).orElseThrow(() -> { throw new ResourceNotFoundException("User not found"); });

            if (user.getName() != null) userToUpdate.setName(user.getName());

            return userRepository.saveAndFlush(userToUpdate);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect request received.");
        }
    }
}
