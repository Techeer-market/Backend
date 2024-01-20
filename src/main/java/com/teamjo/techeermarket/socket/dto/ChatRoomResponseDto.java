package com.teamjo.techeermarket.socket.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.socket.entity.Chat;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ChatRoomResponseDto {

    @JsonIgnore
    private Products products;

    private List<Chat> chatList;

    private String roomName;

    private String name;

    public ChatRoomResponseDto(Products products, List<Chat> chatList, String roomName, String name) {
        this.products = products;
        this.chatList = chatList;
        this.roomName = roomName;
        this.name = name;
    }

}

