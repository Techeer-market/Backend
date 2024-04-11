package com.teamjo.techeermarket.fixture;

import static com.teamjo.techeermarket.fixture.ProductsFixtures.TEST_CREATE_ROOM_USER_PRODUCTINFO;
import static com.teamjo.techeermarket.fixture.ProductsFixtures.TEST_GET_ROOM_USER_PRODUCTS;

import com.teamjo.techeermarket.domain.chats.dto.response.ChatCreateRes;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;

public class ChatRoomFixtures {
  public static final ChatCreateRes TEST_CREATE_ROOM_RESPONSE =
      ChatCreateRes.builder()
          .chatRoomId(1L)
          .productInfo(TEST_CREATE_ROOM_USER_PRODUCTINFO)
          .build();

  public static final ChatRoom TEST_GET_ROOM_1 =
      ChatRoom.builder()
          .id(1L)
          .products(TEST_GET_ROOM_USER_PRODUCTS)
          .sellerEmail("seller@test.com")
          .sellerEmail("buyer@test.com")
          .build();

  public static final ChatRoom TEST_GET_ROOM_2 =
      ChatRoom.builder()
          .id(2L)
          .products(TEST_GET_ROOM_USER_PRODUCTS)
          .sellerEmail("seller@test.com")
          .sellerEmail("buyer@test.com")
          .build();

}
