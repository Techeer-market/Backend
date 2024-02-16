package com.teamjo.techeermarket.domain.chats.mapper;

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
}
