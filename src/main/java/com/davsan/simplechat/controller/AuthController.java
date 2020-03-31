package com.davsan.simplechat.controller;

import com.davsan.simplechat.model.Role;
import com.davsan.simplechat.security.ApiTokenWrapper;
import com.davsan.simplechat.security.JwtTokenProvider;
import com.davsan.simplechat.security.ProviderToken;
import com.davsan.simplechat.model.User;
import com.davsan.simplechat.service.UserService;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("auth")
public class AuthController {

    private static final JacksonFactory jacksonFactory = new JacksonFactory();

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String CLIENT_ID;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;



    @PostMapping("providertoken")
    public ApiTokenWrapper tokenSignIn(@RequestBody ProviderToken providerToken) {
        HttpTransport transport;
        String tokenString = providerToken.getToken();
        try {
            transport = GoogleNetHttpTransport.newTrustedTransport();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), jacksonFactory)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(tokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                String userId = payload.getSubject();

                User userEntity = userService.findByProviderId(userId, providerToken.getProvider());
                if (userEntity == null) {
                    userEntity = User.builder()
                            .name((String) payload.get("name"))
                            .providerId(userId)
                            .provider(providerToken.getProvider())
                            .imageUrl((String) payload.get("picture"))
                            .build();
                    userEntity = userService.saveUser(userEntity);
                }

                List<Role> roles = new ArrayList<>(); // Todo: TEMP
                roles.add(Role.ROLE_CLIENT); // Todo: TEMP

                return  jwtTokenProvider.createToken(userEntity.getId().toString(), roles);

            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; //TODO: Should return info to client
    }
}
