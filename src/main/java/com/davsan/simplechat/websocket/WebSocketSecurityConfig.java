package com.davsan.simplechat.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
               // .nullDestMatcher().permitAll()
                .simpSubscribeDestMatchers("/ws/user/queue/errors", "/ws/system/notifications").permitAll()
                .simpSubscribeDestMatchers("/ws/topic/**").permitAll()
                .simpDestMatchers("/ws/app/**").permitAll()//hasRole("USER")
                //.simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER")
                //.simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
                .anyMessage().permitAll();//denyAll();


    }

    // TODO: Remove for production, testing only!
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}