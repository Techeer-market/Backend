package com.teamjo.techeermarket.domain.chats.dto.request;

import lombok.Data;

@Data
public class ChatReq {
  private Long chatRoomId; // 방 번호
  private Long senderId;
  private String message;
  private String createdAt;

}
