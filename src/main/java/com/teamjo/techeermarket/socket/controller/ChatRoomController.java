package com.teamjo.techeermarket.socket.controller;

import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.service.UserServiceImpl;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import com.teamjo.techeermarket.socket.dto.ChatRoomResponseDto;
import com.teamjo.techeermarket.socket.entity.ChatRoom;
import com.teamjo.techeermarket.socket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;
    private final UserServiceImpl userServiceImpl;

    /**
     *  채팅방에 입장
     */
    @PostMapping("/room")
    public ResponseEntity<ChatRoomResponseDto> enterChatRoom(@RequestParam("productId") Long productId,
                                                             @RequestParam("roomName") String chatRoomName,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        Users users = userServiceImpl.findUser(userDetailsImpl.getUsername());
        ChatRoom chatRoom = chatService.findChatRoomByName(users,chatRoomName,productId);

        ChatRoomResponseDto responseDto = new ChatRoomResponseDto(chatRoom.getProducts(), chatService.findChatList(chatRoom.getId()),
                chatRoom.getChatRoomName(), users.getName());
        return ResponseEntity.ok(responseDto);
    }


    /**
     *  상품에 해당하는 채팅 목록 가져오기
     */
    @GetMapping("/list/{product_id}")
    public ResponseEntity<List<Long>> productChatList(@PathVariable("product_id") Long productId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        List<ChatRoom> chatRooms = chatService.findByProductId(productId);

        // 최근 업데이트순으로 정렬
        chatRooms.sort(Comparator.comparing(ChatRoom::getUpdatedAt).reversed());

        // 해당 상품과 관련된 채팅방에 속한 구매자의 ID 목록 추출
        List<Long> buyerIds = chatRooms.stream()
                .filter(chatRoom -> chatRoom.getBuyer() != null) // 판매자가 아닌 경우에만 필터링
                .map(chatRoom -> chatRoom.getBuyer().getId())
                .collect(Collectors.toList());

        //아니다 response값으로 list 형식으로 user_id / users 테이블에 name 칼럼 / 마지막 message 를 리턴하는 걸로 수정

        return ResponseEntity.ok(buyerIds);
    }


    /**
     * 상품 구매 -> user_purchase table에 buyer_id로 추가
     * products state -> SOLD 로 변경
     */
//    @PostMapping("/buy/{productId}")
//    public



}
