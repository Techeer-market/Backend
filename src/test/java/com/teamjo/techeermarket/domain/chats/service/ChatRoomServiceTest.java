package com.teamjo.techeermarket.domain.chats.service;

import static com.teamjo.techeermarket.fixture.ChatRoomFixtures.TEST_GET_ROOM_1;
import static com.teamjo.techeermarket.fixture.ChatRoomFixtures.TEST_GET_ROOM_2;
import static com.teamjo.techeermarket.fixture.UserFixtures.TEST_GET_ROOM_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.teamjo.techeermarket.domain.chats.dto.response.ChatRoomRes;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.chats.repository.ChatRoomRepository;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceTest {

  @InjectMocks
  private ChatRoomService chatRoomService;
  @Mock
  private ChatRoomRepository chatRoomRepository;
  @Mock
  private UserRepository userRepository;


  @Test
  @DisplayName("Service 채팅방 리스트 조회 - 채팅방이 존재하는 경우")
  void findChatRoomByUserId() {
    // given
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

    List<ChatRoom> chatRoomList = new ArrayList<>();
    chatRoomList.add(TEST_GET_ROOM_1);
    chatRoomList.add(TEST_GET_ROOM_2);

    List<Object[]> outputData = new ArrayList<>();
    for (ChatRoom chatRoom : chatRoomList) {
      Object[] data = new Object[]{
          chatRoom.getId(),
          chatRoom.getProducts().getId(),
          chatRoom.getProducts().getTitle(),
          chatRoom.getProducts().getLocation(),
          chatRoom.getProducts().getPrice(),
          chatRoom.getProducts().getThumbnail(),
          chatRoom.getSellerEmail(),
          chatRoom.getBuyerEmail()
      };
      outputData.add(data);
    }

    Page<Object[]> page = new PageImpl<>(outputData, pageable, outputData.size());

    when(chatRoomRepository.findByUserIn(any(), eq(pageable))).thenReturn(page);
    when(userRepository.findUserByEmail(any())).thenReturn(Optional.ofNullable(TEST_GET_ROOM_USER));

    // when
    List<ChatRoomRes> chatRoomResList = chatRoomService.findChatRoomByUserId("test", 1, 10);

    // then
    assertThat(chatRoomResList.size()).isEqualTo(2);
  }

}
