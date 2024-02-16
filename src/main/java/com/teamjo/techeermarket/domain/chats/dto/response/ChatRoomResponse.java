package com.teamjo.techeermarket.domain.chats.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponse {
  private Long id;
  private Long productId;
  private String productTitle;
  private String productLocation;
  private int productPrice;
  private String productThumbnail;
  private String sellerEmail;
  private String buyerEmail;
}
