package com.teamjo.techeermarket.domain.chats.mapper;

import com.teamjo.techeermarket.domain.chats.dto.response.ChatRes;
import com.teamjo.techeermarket.domain.chats.entity.Chat;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.products.entity.Products;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
  public Chat toEntity(ChatRoom chatRoom, String senderEmail, String message) {
    return Chat.builder()
        .chatRoom(chatRoom)
        .senderEmail(senderEmail)
        .message(message)
        .build();
  }

  public ChatRes toChatResDtoList(Chat chat) {
    return ChatRes.builder()
        .senderEmail(chat.getSenderEmail())
        .message(chat.getMessage())
        .createdAt(chat.getCreatedAt())
        .build();
  }
}
