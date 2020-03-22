package com.davsan.simplechat.security;

import com.davsan.simplechat.model.Role;
import com.davsan.simplechat.model.User;
import com.davsan.simplechat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SCUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(UUID.fromString(id)).get();

        if (user == null) {
            throw new UsernameNotFoundException("User '" + id + "' not found");
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(user.getId().toString())//
                //.password("asd")
//                .authorities(user.getRoles())//
                .authorities(Role.ROLE_CLIENT)
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

}
