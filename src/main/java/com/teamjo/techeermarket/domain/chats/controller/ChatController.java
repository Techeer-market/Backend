package com.teamjo.techeermarket.domain.chats.controller;

import com.teamjo.techeermarket.domain.chats.dto.request.ChatReq;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRes;
import com.teamjo.techeermarket.domain.chats.service.ChatService;
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
public class ChatController {

  private final SimpMessageSendingOperations template;
  private final ChatService chatService;

  @MessageMapping("/api/chat/sendMessage")
  public void sendMessage(@Payload ChatReq chat) {
    chatService.saveMessage(chat);
    template.convertAndSend("/sub/chat/room/" + chat.getChatRoomId(), chat);
  }

  @GetMapping("/api/chat/{chatRoomId}")
  public ResponseEntity<ChatRes> getAllChat(
      @PathVariable Long chatRoomId
  ) {
    ChatRes response = chatService.getMessage(chatRoomId);
    return ResponseEntity.ok(response);
  }
}
