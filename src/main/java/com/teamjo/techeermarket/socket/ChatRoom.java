package com.teamjo.techeermarket.socket;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatDTO chatDTO, ChatService service){
        if(chatDTO.getType().equals(ChatDTO.MessageType.ENTER)){
            sessions.add(session);
            chatDTO.setMessage(chatDTO.getSender() + " 님이 입장하셨습니다.");
            sendMessage(chatDTO,service);
        }else if(chatDTO.getType().equals(ChatDTO.MessageType.TALK)){
            sendMessage(chatDTO,service);
        }
    }

    public <T> void sendMessage(T message, ChatService service){
        sessions.parallelStream().forEach(session -> service.sendMessage(session,message));
    }
}
