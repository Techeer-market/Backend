package com.teamjo.techeermarket.domain.chats.controller;


import static com.teamjo.techeermarket.fixture.ChatFixtures.TEST_GET_CHAT_RESPONSE;
import static com.teamjo.techeermarket.fixture.ChatRoomFixtures.TEST_CREATE_ROOM_RESPONSE;
import static com.teamjo.techeermarket.fixture.UserFixtures.TEST_CREATE_ROOM_USER_DETAIL;
import static com.teamjo.techeermarket.fixture.UserFixtures.TEST_GET_CHAT_USER_DETAIL;
import static com.teamjo.techeermarket.fixture.UserFixtures.TEST_GET_ROOM_USER_DETAIL;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamjo.techeermarket.domain.chats.service.ChatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ChatController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ChatControllerTest {

  @MockBean
  private SimpMessageSendingOperations messagingTemplate;
  @MockBean
  private ChatService chatService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;



  private String toJsonString(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  @Test
  @DisplayName("Controller 채팅 리스트 조화")
  void getAllChat() throws Exception {

    //when
    when(chatService.getMessage(anyLong())).thenReturn(TEST_GET_CHAT_RESPONSE);

    //then
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/chat/{chatRoomId}", 1L)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(TEST_GET_CHAT_USER_DETAIL))
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.chatInfoList", hasSize(3)))
        .andDo(print());
  }

}
