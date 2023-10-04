package com.teamjo.techeermarket.socket;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    private final ChatService chatService;
    private final SimpMessageSendingOperations sendingOperations;
//
//    @MessageMapping("/message")
//    public void enter(SendMessage message) {
//        if (Message.MessageType.ENTER.equals(message.getType())) {
//            message.setDetailMessage(message.getSenderId()+"님이 입장하였습니다.");
//            chatService.sendMessage(Message.MessageType.ENTER,message.roomId, message.detailMessage, message.senderId);
//        }
//        else chatService.sendMessage(Message.MessageType.TALK,message.roomId, message.detailMessage, message.senderId);
//        log.info("roomID = {}", message.getRoomId());
//        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
//    }
//
//
//    @Data
//    private static class SendMessage{
//        Message.MessageType type;
//        Long roomId;
//        String detailMessage;
//        Long senderId;
//    }
}
