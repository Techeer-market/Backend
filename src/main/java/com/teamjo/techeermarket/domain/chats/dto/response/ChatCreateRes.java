package com.teamjo.techeermarket.domain.chats.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatCreateRes {
  private Long chatRoomId;
  private ProductInfo productInfo;

}
