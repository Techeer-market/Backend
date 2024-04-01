package com.teamjo.techeermarket.fixture;

import static com.teamjo.techeermarket.fixture.ProductsFixtures.TEST_CREATE_ROOM_USER_PRODUCTINFO;

import com.teamjo.techeermarket.domain.chats.dto.response.ChatCreateRes;

public class ChatRoomFixtures {
  public static final ChatCreateRes TEST_CREATE_ROOM_RESPONSE =
      ChatCreateRes.builder()
          .chatRoomId(1L)
          .productInfo(TEST_CREATE_ROOM_USER_PRODUCTINFO)
          .build();

}
