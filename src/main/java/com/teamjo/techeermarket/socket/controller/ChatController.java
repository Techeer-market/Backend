package com.teamjo.techeermarket.socket.controller;

import com.teamjo.techeermarket.socket.dto.ChatDto;
import com.teamjo.techeermarket.socket.entity.ChatType;
import com.teamjo.techeermarket.socket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;

    private final ChatService chatService;

    /**
     *  채팅방 입장
     */
    @MessageMapping(value = "/chat/enter")
    public void enter (ChatDto chatDto) {
        chatDto.setMessage(chatDto.getName() + "님이 채팅방에 참여하셨습니다."); // 추후 삭제..?
        chatDto.setType(ChatType.JOIN);
        template.convertAndSend("/sub/chat/room" + chatDto.getRoomId(),chatDto);
    }


    /**
     * 채팅 메시지 보내기
     */
    @MessageMapping(value = "/chat/send")
    public void send(ChatDto chatDto) {
        chatService.saveChat(chatDto);
        template.convertAndSend("/sub/chat/room" + chatDto.getRoomId(),chatDto);
    }

}
