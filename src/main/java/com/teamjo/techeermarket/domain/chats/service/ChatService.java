package com.teamjo.techeermarket.domain.chats.service;

import com.teamjo.techeermarket.domain.chats.dto.request.ChatReq;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatInfo;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ProductInfo;
import com.teamjo.techeermarket.domain.chats.entity.Chat;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.chats.mapper.ChatMapper;
import com.teamjo.techeermarket.domain.chats.repository.ChatRepository;
import com.teamjo.techeermarket.domain.chats.repository.ChatRoomRepository;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.global.exception.product.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.teamjo.techeermarket.global.exception.chat.ChatNotFoundException;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatRoomRepository chatRoomRepository;
  private final ProductRepository productRepository;
  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;
  private final ProductMapper productMapper;

  @Transactional
  public void saveMessage(ChatReq chatReq) {

    ChatRoom chatRoom = chatRoomRepository.findById(chatReq.getChatRoomId())
        .orElseThrow(ChatNotFoundException::new);

    Chat chat = chatMapper.toEntity(chatRoom, chatReq.getSenderEmail(), chatReq.getMessage());

    chatRepository.save(chat);
  }

  @Transactional(readOnly = true)
  public ChatRes getMessage(Long chatRoomId) {
    // DB 조회 (채팅 리스트, 제품)
    List<Chat> chatList = chatRepository.findByChatRoomId(chatRoomId);

    ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
        .orElseThrow(ChatNotFoundException::new);
    Long productId = chatRoom.getId();

    Products products = productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);

    // 반환값 제작 (채팅 기록, 제품 정보, 첫 채팅 시각)
    List<ChatInfo> response = chatList.stream()
        .map(chatMapper::toChatResDtoList)
        .collect(Collectors.toList());

    ProductInfo productInfo = productMapper.toProductInfo(products);

    String chatCreateAt = chatList.get(chatList.size() - 1).getCreatedAt();

    return chatMapper.toChatResDto(response, productInfo, chatCreateAt);
  }
}
