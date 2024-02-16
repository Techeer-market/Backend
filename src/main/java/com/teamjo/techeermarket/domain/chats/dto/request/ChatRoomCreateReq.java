package com.teamjo.techeermarket.domain.chats.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCreateReq {
  private Long productId;
  private String sellerEmail;

}
