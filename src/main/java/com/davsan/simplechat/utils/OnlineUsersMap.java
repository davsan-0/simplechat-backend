package com.davsan.simplechat.utils;

import com.davsan.simplechat.websocket.OnlineStatusMessage;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Keeps track of all online users in-memory.
 *
 * @author David Sandström
 */
@Component
public class OnlineUsersMap {
    private final int EXPIRATION_TIME = 25;
    private ExpiringMap<UUID, ArrayList<UUID>> onlineUsersMap;

    @Autowired
    private SimpMessagingTemplate webSocket;

    public OnlineUsersMap() {
        onlineUsersMap = ExpiringMap.builder().expirationPolicy(ExpirationPolicy.ACCESSED).expirationListener((key, chatIds) -> {
            System.out.println("key " + key.toString() + " has expired!");
            OnlineStatusMessage onlineStatusMessage = new OnlineStatusMessage((UUID) key, false);
            for (UUID chatId : (List<UUID>) chatIds) {
                webSocket.convertAndSend("/ws/topic/chats/" + chatId.toString() + "/online", onlineStatusMessage);
            }
        }).expiration(EXPIRATION_TIME, TimeUnit.SECONDS).build();
    }

    public boolean containsKey(UUID key) {
        return onlineUsersMap.containsKey(key);
    }

    public ArrayList<UUID> get(UUID key) {
        return onlineUsersMap.get(key);
    }

    public void put(UUID key, ArrayList<UUID> value) {
        onlineUsersMap.put(key, value);
    }

   /* public void append(UUID key, UUID value) {
        if (onlineUsersMap.containsKey(key)) {
            ArrayList<UUID> chats = onlineUsersMap.get(key);
            if (!chats.contains(value)) chats.add(value);
        } else {
            ArrayList<UUID> chats = new ArrayList<>();
            chats.add(value);
            onlineUsersMap.put(key, chats);
        }

    }*/

}
