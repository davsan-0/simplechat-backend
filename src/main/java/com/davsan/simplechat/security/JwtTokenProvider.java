package com.davsan.simplechat.security;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.davsan.simplechat.dto.Mapper;
import com.davsan.simplechat.dto.UserDTO;
import com.davsan.simplechat.model.Role;
import com.davsan.simplechat.model.User;
import com.davsan.simplechat.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JwtTokenProvider {

    /**
     * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
     * microservices environment, this key would be kept on a config-server.
     */
    @Value("${spring.security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${spring.security.jwt.token.expire-length}")
    private long validityInMilliseconds;

//    @Autowired
//    private MyUserDetails myUserDetails;

    @Autowired
    private UserService userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public ApiTokenWrapper createToken(String id, List<Role> roles) {

        Claims claims = Jwts.claims().setSubject(id);
        claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        ApiTokenWrapper wrapper = new ApiTokenWrapper();
        wrapper.setExpiresAt(validity);
        wrapper.setAccessToken(Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact());

        return wrapper;
    }

//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }
//

    public Authentication getAuthentication(String token) {
        UUID id = getId(token);
        User user = userService.findById(id);
        //System.out.println(user.getChats());
        //UserDTO userDto = Mapper.UserToDTO(user);
        List<Role> list = new ArrayList<>(); // TEMP
        list.add(Role.ROLE_CLIENT); // TEMP
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getId(), "", list);
        return auth;
    }

    public UUID getId(String token) {
        return UUID.fromString(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            System.out.println("Token validated");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Expired or invalid JWT Token");
        }
    }

}