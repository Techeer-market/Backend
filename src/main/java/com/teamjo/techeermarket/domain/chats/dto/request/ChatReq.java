package com.teamjo.techeermarket.domain.chats.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatReq {
  private Long chatRoomId; // 방 번호
  private String senderEmail;
  private String message;

}
