package com.teamjo.techeermarket.socket.dto;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;
import com.teamjo.techeermarket.socket.entity.ChatRoom;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoomDto {

    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom toEntity(Products products, Users buyer){
        return ChatRoom.builder()
                .buyer(buyer)
                .products(products)
                .build();
    }
}
