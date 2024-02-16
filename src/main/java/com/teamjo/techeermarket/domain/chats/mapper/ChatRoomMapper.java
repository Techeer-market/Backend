package com.teamjo.techeermarket.domain.chats.mapper;

import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.products.entity.Products;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapper {
  public ChatRoom toEntity(Products products, String sellerEmail, String buyerEmail) {
    return ChatRoom.builder()
        .products(products)
        .sellerEmail(sellerEmail)
        .buyerEmail(buyerEmail)
        .build();
  }


}
