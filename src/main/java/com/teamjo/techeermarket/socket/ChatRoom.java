package com.teamjo.techeermarket.socket;

import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.*;

@Slf4j
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chatRoom")
public class ChatRoom extends BaseEntity {
    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "room_uuid", length = 36, nullable = false, updatable = false)
    private UUID roomUuId;

    private String name;

    private UUID productUuId;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessage> messages;

    static Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(UUID roomUuId, String name, UUID productUuId){
        this.productUuId = productUuId;
        this.roomUuId = roomUuId;
        this.name = name;
    }

    public void handleAction(WebSocketSession session, ChatMessage chatMessage, ChatService service){
        log.info("session :: {}", session);
        if(chatMessage.getType().equals(MessageType.ENTER)){
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + " 님이 입장하셨습니다.");
            log.info("session length :: {}", sessions.size());
            sendMessage(chatMessage,service);
        }else if(chatMessage.getType().equals(MessageType.TALK)){
//            if(!sessions.contains(session)) {
//                log.info("session added :: {}",session);
//                sessions.add(session);
//            }
            log.info("handleAction :: chatDTO :: {}", chatMessage);
            sendMessage(chatMessage,service);
        }
    }

    public <T> void sendMessage(T message, ChatService service){
        sessions.parallelStream().forEach(session -> service.sendMessage(session,message));
    }
}
