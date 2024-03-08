package com.teamjo.techeermarket.domain.chats.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRes {
  private ProductInfo productInfo;
  private String chatCreateAt;
  private List<ChatInfo> chatInfoList;
}