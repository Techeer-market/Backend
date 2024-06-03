package com.teamjo.techeermarket.domain.chats.controller;

import com.teamjo.techeermarket.domain.chats.dto.response.ChatCreateRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRoomRes;
import com.teamjo.techeermarket.domain.chats.dto.response.deleteRes;
import com.teamjo.techeermarket.domain.chats.service.ChatRoomService;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  /*
   * @Describe : 채팅방 제작
   * @Param1 : chatRoomCreateDto(ChatRoomCreateDto) 제품 id, 판매자 id (sellerId)
   * @Param2 : userDetailsImpl(UserDetailsImpl) 구매자 id(로그인 유저)
   */
  @PostMapping("/create/{productId}")
  public ResponseEntity<ChatCreateRes> createRoom(
      @PathVariable Long productId,
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
      @RequestParam Long chatRoomId
  ) {
    ChatCreateRes chatCreateRes = chatRoomService.createChatRoom(productId, userDetailsImpl.getUsername(), chatRoomId);

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

  /*
   * @Describe : 채팅방 삭제
   * @Param1 : 삭제 채팅방 번호 (chatRoomId)
   */
  @DeleteMapping("/room/{chatRoomId}")
  public deleteRes deleteRoom(
      @PathVariable Long chatRoomId,
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
  ) {
    if (!chatRoomService.deleteChatRoom(chatRoomId, userDetailsImpl.getUsername())) {
      return deleteRes.builder()
              .code(404)
              .response("존재하지 않은 채팅이거나 본인의 채팅방이 아닙니다.")
              .build();
    }

    return deleteRes.builder()
        .code(200)
        .response("성공하였습니다.")
        .build();
  }


}
