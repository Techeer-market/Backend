package com.teamjo.techeermarket.domain.chats.controller;

import com.teamjo.techeermarket.domain.chats.dto.response.ChatCreateRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRoomRes;
import com.teamjo.techeermarket.domain.chats.service.ChatRoomService;
import com.teamjo.techeermarket.domain.products.service.ProductService;
import com.teamjo.techeermarket.domain.users.service.UserService;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  @PostMapping("/create/{productId}")
  public ResponseEntity<ChatCreateRes> createRoom(
      @PathVariable Long productId,
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
  ) {
    ChatCreateRes chatCreateRes = chatRoomService.createChatRoom(productId, userDetailsImpl.getUsername());

    return ResponseEntity.status(HttpStatus.CREATED).body(chatCreateRes);
  }

  /*
   * @Describe : 채팅방 리스트 조회
   * @Param1 : userDetailsImpl(UserDetailsImpl) 로그인 유저
   */
  @GetMapping("/room")
  public ResponseEntity<List<ChatRoomRes>> getAllChatRoom(
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize
  ) {
    List<ChatRoomRes> chatRooms = chatRoomService.findChatRoomByUserId(userDetailsImpl.getUsername(), pageNo, pageSize);

    return ResponseEntity.ok(chatRooms);
  }


}
