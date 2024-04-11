package com.teamjo.techeermarket.domain.chats.controller;

import static com.teamjo.techeermarket.fixture.ChatRoomFixtures.TEST_CREATE_ROOM_RESPONSE;
import static com.teamjo.techeermarket.fixture.ChatRoomFixtures.TEST_GET_ROOM_RESPONSE_3;
import static com.teamjo.techeermarket.fixture.ChatRoomFixtures.TEST_GET_ROOM_RESPONSE_4;
import static com.teamjo.techeermarket.fixture.UserFixtures.TEST_CREATE_ROOM_USER_DETAIL;
import static com.teamjo.techeermarket.fixture.UserFixtures.TEST_GET_ROOM_USER_DETAIL;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRoomRes;
import com.teamjo.techeermarket.domain.chats.repository.ChatRoomRepository;
import com.teamjo.techeermarket.domain.chats.service.ChatRoomService;
import com.teamjo.techeermarket.domain.chats.service.ChatService;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.domain.products.service.ProductService;
import com.teamjo.techeermarket.domain.users.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

@WebMvcTest(ChatRoomController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ChatRoomControllerTest {

  @MockBean
  private UserService userService;
  @MockBean
  private ProductService productService;
  @MockBean
  private ChatRoomService chatRoomService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Controller 채팅방 생성")
  void createChatRoom() throws Exception {

    //when
    when(chatRoomService.createChatRoom(any(), any())).thenReturn(TEST_CREATE_ROOM_RESPONSE);

    //then
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/chat/create/{productId}", 1L)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(TEST_CREATE_ROOM_USER_DETAIL))
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.chatRoomId").value(TEST_CREATE_ROOM_RESPONSE.getChatRoomId()))
        .andDo(print());
  }

  @Test
  @DisplayName("Controller 채팅방 리스트 조회 - 채팅방이 존재하는 경우")
  void getAllChatRoomExistence() throws Exception {

    //when
    List<ChatRoomRes> outputData = new ArrayList<>();
    outputData.add(TEST_GET_ROOM_RESPONSE_3);
    outputData.add(TEST_GET_ROOM_RESPONSE_4);

    when(chatRoomService.findChatRoomByUserId(any(), anyInt(), anyInt())).thenReturn(outputData);

    //then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/chat/room")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(TEST_GET_ROOM_USER_DETAIL))
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andDo(print());

  }

  @Test
  @DisplayName("Controller 채팅방 리스트 조회 - 채팅방이 존재하지 않는 경우")
  void getAllChatRoomNonexistent() throws Exception {

    //when
    List<ChatRoomRes> outputData = new ArrayList<>();

    when(chatRoomService.findChatRoomByUserId(any(), anyInt(), anyInt())).thenReturn(outputData);

    //then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/chat/room")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(TEST_GET_ROOM_USER_DETAIL))
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)))
        .andDo(print());

  }
}
