//package com.teamjo.techeermarket.socket;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.teamjo.techeermarket.socket.entity.ChatRoom;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.*;
//
//@Slf4j
//@Data
//@Service
//public class ChatService {
//    private final ObjectMapper mapper;
//    private Map<UUID, ChatRoom> chatRooms;
//
//    @PostConstruct
//    private void init(){
//        chatRooms = new LinkedHashMap<>();
//    }
//
//    public List<ChatRoom> findAllRoom(){
//        return new ArrayList<>(chatRooms.values());
//    }
//
//
//    public ChatRoom findRoomByProductId(UUID productUuid){
//        return chatRooms.get(productUuid);
//    }
//
//
//    public ChatRoom createRoom(String name,UUID productUuid){
//        String roomId = UUID.randomUUID().toString();
//
//        ChatRoom room = ChatRoom.builder()
//                .roomId(roomId)
//                .productUuId(productUuid)
//                .name(name)
//                .build();
//        chatRooms.put(productUuid, room);
//
//
//        return room;
//    }
//
//    public <T> void sendMessage(WebSocketSession session, T message){
//        try{
//            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
//        }catch (IOException e){
//            log.info("ChatService :: sendMessage :: Error :: {}",e);
//        }
//    }
//}
