package com.teamjo.techeermarket.domain.chats.dto.request;

import lombok.Data;

@Data
public class ChatReq {
  private Long chatRoomId; // 방 번호
  private String senderEmail;
  private String message;
  private String createdAt;

}
