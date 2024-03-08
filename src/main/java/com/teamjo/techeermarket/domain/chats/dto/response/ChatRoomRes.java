package com.teamjo.techeermarket.domain.chats.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomRes {
  private Long id;
  private Long productId;
  private String productTitle;
  private String productLocation;
  private String currentChatAt;
  private int productPrice;
  private String productThumbnail;
  private String chatPartnerName;
}
