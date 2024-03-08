package com.teamjo.techeermarket.domain.chats.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRes {
  private ProductInfo productInfo;
  private String chatCreateAt;
  private List<ChatInfo> chatInfoList;
}
