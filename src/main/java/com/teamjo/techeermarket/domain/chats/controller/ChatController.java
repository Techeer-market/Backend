package com.teamjo.techeermarket.domain.chats.controller;

import com.teamjo.techeermarket.domain.chats.dto.request.ChatReq;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRoomRes;
import com.teamjo.techeermarket.domain.chats.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    chatService.saveMessage(chat);
    template.convertAndSend("/sub/chat/room/" + chat.getChatRoomId(), chat);
  }

  @GetMapping("/api/chat/{chatRoomId}")
  public ResponseEntity<List<ChatRes>> getAllChat(
      @PathVariable Long chatRoomId
  ) {
    List<ChatRes> response = chatService.getMessage(chatRoomId);
    return ResponseEntity.ok(response);
  }
}
