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
  public Chat toEntity(ChatRoom chatRoom, Long senderId, String message) {
    return Chat.builder()
        .chatRoom(chatRoom)
        .senderId(senderId)
        .message(message)
        .build();
  }

  public ChatInfo toChatResDtoList(Chat chat) {
    return ChatInfo.builder()
        .senderId(chat.getSenderId())
        .message(chat.getMessage())
        .createdAt(chat.getCreatedAt())
        .build();
  }

  public ChatRes toChatResDto (Long chatRoomId, List<ChatInfo> response, ProductInfo productInfo, String chatCreateAt) {
    return ChatRes.builder()
        .chatRoomId(chatRoomId)
        .chatInfoList(response)
        .productInfo(productInfo)
        .chatCreateAt(chatCreateAt)
        .build();
  }

  public ChatCreateRes toChatCreateNewResDto (Long chatRoomId, ProductInfo productInfo) {
    return ChatCreateRes.builder()
        .chatRoomId(chatRoomId)
        .productInfo(productInfo)
        .build();
  }

  public ChatCreateRes toChatCreateResDto (Long chatRoomId, ProductInfo productInfo, String chatCreateAt, List<ChatInfo> response) {
    return ChatCreateRes.builder()
        .chatRoomId(chatRoomId)
        .productInfo(productInfo)
        .chatInfoList(response)
        .chatCreateAt(chatCreateAt)
        .build();
  }
}
