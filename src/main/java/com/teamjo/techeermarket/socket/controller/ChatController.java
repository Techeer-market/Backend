package com.teamjo.techeermarket.socket.controller;

import com.teamjo.techeermarket.socket.chatService.ChatService;
import com.teamjo.techeermarket.socket.entity.Chat;
import com.teamjo.techeermarket.socket.entity.ChatRoom;
import com.teamjo.techeermarket.socket.repository.ChatRepository;
import com.teamjo.techeermarket.socket.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
@Controller
@Slf4j
@AllArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Chat chat) {
//        log.info("chat");
        chatService.saveChatMessage(chat);
//        log.info(chat);
        messagingTemplate.convertAndSend("/topic/public/" + chat.getChatRoom().getChatRoomName() , chat);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("userName", chat.getUsers());
        messagingTemplate.convertAndSend("/topic/public/" + chat.getChatRoom().getChatRoomName(), chat);
    }

    @MessageMapping("/chat.createChatRoom")
    public void createChatRoom(@Payload ChatRoom chatRoom){
        ChatRoom createdRoom = chatService.createChatRoom(chatRoom.getChatRoomName());
        messagingTemplate.convertAndSend("/topic/public/", createdRoom);
    }
}
