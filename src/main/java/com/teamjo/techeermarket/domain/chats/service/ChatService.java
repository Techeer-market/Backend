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
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatRoomRepository chatRoomRepository;
  private final ProductRepository productRepository;
  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;

  @Transactional
  public void saveMessage(ChatReq chatReq) {
    ChatRoom chatRoom = chatRoomRepository.findById(chatReq.getChatRoomId()).orElseThrow();

    Chat chat = chatMapper.toEntity(chatRoom, chatReq.getSenderEmail(), chatReq.getMessage());

    chatRepository.save(chat);
  }

  @Transactional(readOnly = true)
  public ChatRes getMessage(Long chatRoomId) {
    List<Chat> chatList = chatRepository.findByChatRoomId(chatRoomId);
    Long productId = chatRoomRepository.findById(chatRoomId).get().getProducts().getId();
    Products products = productRepository.findById(productId).get();
    ChatRes chatRes = new ChatRes();

    List<ChatInfo> response = chatList.stream()
        .map(chatMapper::toChatResDtoList)
        .collect(Collectors.toList());

    ProductInfo productInfo = chatMapper.toProductInfo(products);

    String chatCreateAt = chatRepository.findCreateAtByChatRoomId(chatRoomId).get(0);

    chatRes.setChatInfoList(response);
    chatRes.setProductInfo(productInfo);
    chatRes.setChatCreateAt(chatCreateAt);


    return chatRes;
  }
}
