package com.teamjo.techeermarket.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.*;

@Slf4j
@Data
@Service
public class ChatService {
    private final ObjectMapper mapper;
    private Map<UUID,ChatRoom> chatRooms;
    private final ChatRoomRepository chatRoomRepository;
    private final EntityManager entityManager;
    public ChatService(ObjectMapper mapper,ChatRoomRepository chatRoomRepository, EntityManager entityManager){
        this.mapper = mapper;
        this.chatRoomRepository= chatRoomRepository;
        this.entityManager = entityManager;
    }

    @PostConstruct
    private void init(){
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(){
        return new ArrayList<>(chatRooms.values());
    }

    public void updateChatRoom(ChatMessage newMessage, UUID productUuid){
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByProductUuId(productUuid);
        if(chatRoom != null){
            List<ChatMessage> messages = chatRoom.getMessages();
            messages.add(newMessage);
            chatRoom.setMessages(messages);
            chatRoomRepository.save(chatRoom);
        }else {
            log.info("updateChatRoom :: chatRoom is null");
        }
    }


    public ChatRoom findRoomByProductId(UUID productUuid) {
        return chatRoomRepository.findChatRoomByProductUuId(productUuid);
    }

    public ChatRoom createRoom(String name,UUID productUuid){
        if(chatRoomRepository.existsByProductUuId(productUuid)) {
            return chatRoomRepository.findChatRoomByProductUuId(productUuid);
        }
        UUID roomId = UUID.randomUUID();
        ChatRoom room = ChatRoom.builder()
                .roomUuId(roomId)
                .productUuId(productUuid)
                .name(name)
                .build();
        chatRooms.put(productUuid, room);
        chatRoomRepository.save(room);
        return room;
    }




    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        }catch (IOException e){
            log.info("ChatService :: sendMessage :: Error :: {}",e);
        }
    }
}
