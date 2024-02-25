package com.teamjo.techeermarket.domain.chats.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatCreateRes {
  private Long chatRoomId;
  private ProductInfo productInfo;

}
