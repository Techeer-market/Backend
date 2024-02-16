package com.teamjo.techeermarket.domain.chats.controller;

import com.teamjo.techeermarket.domain.chats.dto.request.ChatRoomCreateReq;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRoomResponse;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.chats.service.ChatRoomService;
import com.teamjo.techeermarket.domain.products.dto.response.ProductDetailViewDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.service.ProductService;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.service.UserService;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {
  private final ChatRoomService chatRoomService;
  private final ProductService productService;
  private final UserService userService;

  /*
   * @Describe : 채팅방 제작
   * @Param1 : chatRoomCreateDto(ChatRoomCreateDto) 제품 id, 판매자 id (sellerId)
   * @Param2 : userDetailsImpl(UserDetailsImpl) 구매자 id(로그인 유저)
   */
  @PostMapping("/create")
  public ResponseEntity<Long> createRoom(
      @RequestBody ChatRoomCreateReq chatRoomCreateReq,
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
  ) {
    Long chatRoomId = chatRoomService.createChatRoom(chatRoomCreateReq, userDetailsImpl.getUsername());

    return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomId);
  }

  /*
   * @Describe : 채팅방 리스트 조회
   * @Param1 : userDetailsImpl(UserDetailsImpl) 로그인 유저
   */
  @GetMapping
  public ResponseEntity<List<ChatRoomResponse>> getAllChat(
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
  ) throws IOException {
    List<ChatRoomResponse> chatRooms = chatRoomService.findChatRoomByUserId(userDetailsImpl.getUsername());

    return ResponseEntity.ok(chatRooms);
  }


}
