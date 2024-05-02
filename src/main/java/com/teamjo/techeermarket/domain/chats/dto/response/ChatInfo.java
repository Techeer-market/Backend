package com.teamjo.techeermarket.domain.chats.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatInfo {
  private Long senderId;
  private String message;
  private String createdAt;
}
