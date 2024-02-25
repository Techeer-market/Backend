package com.teamjo.techeermarket.domain.chats.mapper;

import com.teamjo.techeermarket.domain.chats.dto.response.ChatInfo;
import com.teamjo.techeermarket.domain.chats.dto.response.ProductInfo;
import com.teamjo.techeermarket.domain.chats.entity.Chat;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.products.entity.Products;
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

  public ProductInfo toProductInfo(Products product){
    return ProductInfo.builder()
        .productId(product.getId())
        .title(product.getTitle())
        .thumbnailURL(product.getThumbnail())
        .name(product.getUsers().getName())
        .userId(product.getUsers().getId())
        .price(product.getPrice())
        .createdAt(product.getCreatedAt())
        .likes(product.getHeart())
        .views(product.getViews())
        .build();
  }
}
