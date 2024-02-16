package com.teamjo.techeermarket.domain.chats.controller;

import com.teamjo.techeermarket.domain.chats.dto.request.ChatReq;
import com.teamjo.techeermarket.domain.chats.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/api/chat") - ws에서 지원 x
public class ChatController {

  private final SimpMessageSendingOperations template;
  private final ChatService chatService;

//  // 처음 시작하는 체팅 -> 채팅방 제작
//  // 기존에 존재하는 채팅 -> 채팅방 id 반환만
//  @MessageMapping("/api/chat/enterRoom")
//  public void enterUser(@Payload ChatReq chat) {
//
//  }

  @MessageMapping("/api/chat/sendMessage")
  public void sendMessage(@Payload ChatReq chat) {
    System.out.println(chat.getChatRoomId());
    chatService.saveMessage(chat);
    template.convertAndSend("/sub/chat/room/" + chat.getChatRoomId(), chat);
  }
}
