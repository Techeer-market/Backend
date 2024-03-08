package com.teamjo.techeermarket.domain.chats.mapper;

import com.teamjo.techeermarket.domain.chats.dto.response.ChatCreateRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatInfo;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ProductInfo;
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

  public ChatInfo toChatResDtoList(Chat chat) {
    return ChatInfo.builder()
        .senderEmail(chat.getSenderEmail())
        .message(chat.getMessage())
        .createdAt(chat.getCreatedAt())
        .build();
  }

  public ChatRes toChatResDto (List<ChatInfo> response, ProductInfo productInfo, String chatCreateAt) {
    return ChatRes.builder()
        .chatInfoList(response)
        .productInfo(productInfo)
        .chatCreateAt(chatCreateAt)
        .build();
  }

  public ChatCreateRes toChatCreateResDto (Long chatRoomId, ProductInfo productInfo) {
    return ChatCreateRes.builder()
        .chatRoomId(chatRoomId)
        .productInfo(productInfo)
        .build();
  }
}
