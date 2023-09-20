//package com.teamjo.techeermarket.socket;
//
//import lombok.Builder;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//
//@Slf4j
//@Data
//public class ChatRoom {
//    private UUID productUuId;
//    private String roomId;
//    private String name;
//    static Set<WebSocketSession> sessions = new HashSet<>();
//
//    @Builder
//    public ChatRoom(String roomId, String name, UUID productUuId){
//        this.productUuId = productUuId;
//        this.roomId = roomId;
//        this.name = name;
//    }
//
//    public void handleAction(WebSocketSession session, ChatDTO chatDTO, ChatService service){
//        log.info("session :: {}", session);
//        if(chatDTO.getType().equals(ChatDTO.MessageType.ENTER)){
//            sessions.add(session);
//            chatDTO.setMessage(chatDTO.getSender() + " 님이 입장하셨습니다.");
//            log.info("session length :: {}", sessions.size());
//            sendMessage(chatDTO,service);
//        }else if(chatDTO.getType().equals(ChatDTO.MessageType.TALK)){
////            if(!sessions.contains(session)) {
////                log.info("session added :: {}",session);
////                sessions.add(session);
////            }
//            log.info("handleAction :: chatDTO :: {}",chatDTO);
//            sendMessage(chatDTO,service);
//        }
//    }
//
//    public <T> void sendMessage(T message, ChatService service){
//        sessions.parallelStream().forEach(session -> service.sendMessage(session,message));
//    }
//}
