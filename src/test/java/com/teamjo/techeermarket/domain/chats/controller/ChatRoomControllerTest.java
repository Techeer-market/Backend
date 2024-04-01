package com.teamjo.techeermarket.domain.chats.controller;

import static com.teamjo.techeermarket.fixture.ChatRoomFixtures.TEST_CREATE_ROOM_RESPONSE;
import static com.teamjo.techeermarket.fixture.ProductsFixtures.TEST_CREATE_ROOM_USER_PRODUCTS;
import static com.teamjo.techeermarket.fixture.UserFixtures.TEST_CREATE_ROOM_USER;
import static com.teamjo.techeermarket.fixture.UserFixtures.TEST_CREATE_ROOM_USER_DETAIL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamjo.techeermarket.domain.chats.service.ChatRoomService;
import com.teamjo.techeermarket.domain.chats.service.ChatService;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.domain.products.service.ProductService;
import com.teamjo.techeermarket.domain.users.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
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
  @MockBean
  private ProductRepository productRepository;
  @MockBean
  private ChatService chatService;
  @MockBean
  private SimpMessageSendingOperations template;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;


  private String toJsonString(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }


  @Test
  @DisplayName("Controller 채팅방 생성")
  void createChatRoom() throws Exception {

    //when
    when(userService.findUser(any())).thenReturn(TEST_CREATE_ROOM_USER);
    when(productRepository.findById(any())).thenReturn(
        Optional.ofNullable(TEST_CREATE_ROOM_USER_PRODUCTS));
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
}
