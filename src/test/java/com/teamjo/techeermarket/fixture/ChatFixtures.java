//package com.teamjo.techeermarket.fixture;
//
//import static com.teamjo.techeermarket.fixture.ProductsFixtures.TEST_CREATE_ROOM_USER_PRODUCTINFO;
//
//import com.teamjo.techeermarket.domain.chats.dto.response.ChatInfo;
//import com.teamjo.techeermarket.domain.chats.dto.response.ChatRes;
//import java.util.Arrays;
//
//public class ChatFixtures {
//
//  public static final ChatInfo TEST_CHAT_INFO_1 =
//      ChatInfo.builder()
//          .senderId(1L)
//          .message("testMessage1")
//          .message("testCreateAt1")
//          .build();
//
//  public static final ChatInfo TEST_CHAT_INFO_2 =
//      ChatInfo.builder()
//          .senderId(2L)
//          .message("testMessage2")
//          .message("testCreateAt2")
//          .build();
//
//  public static final ChatInfo TEST_CHAT_INFO_3 =
//      ChatInfo.builder()
//          .senderId(3L)
//          .message("testMessage3")
//          .message("testCreateAt3")
//          .build();
//
//  public static final ChatRes TEST_GET_CHAT_RESPONSE =
//      ChatRes.builder()
//          .productInfo(TEST_CREATE_ROOM_USER_PRODUCTINFO)
//          .chatCreateAt("11:11:11")
//          .chatInfoList(Arrays.asList(TEST_CHAT_INFO_1, TEST_CHAT_INFO_2, TEST_CHAT_INFO_3))
//          .build();
//
//
//}
