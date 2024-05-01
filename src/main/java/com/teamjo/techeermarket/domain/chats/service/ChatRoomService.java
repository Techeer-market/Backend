package com.teamjo.techeermarket.domain.chats.service;


import com.teamjo.techeermarket.domain.chats.dto.response.ChatCreateRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatInfo;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRoomRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ProductInfo;
import com.teamjo.techeermarket.domain.chats.entity.Chat;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.chats.mapper.ChatMapper;
import com.teamjo.techeermarket.domain.chats.mapper.ChatRoomMapper;
import com.teamjo.techeermarket.domain.chats.repository.ChatRepository;
import com.teamjo.techeermarket.domain.chats.repository.ChatRoomRepository;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.exception.product.ProductNotFoundException;
import com.teamjo.techeermarket.global.exception.user.UserNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;
  private final ChatRepository chatRepository;
  private final ProductRepository productRepository;
  private final ChatRoomMapper chatRoomMapper;
  private final UserRepository userRepository;
  private final ChatMapper chatMapper;
  private final ProductMapper productMapper;

  @Transactional
  public ChatCreateRes createChatRoom(Long productId, String buyer, Long chatRoomId) {

    Products product = productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);

    if (chatRoomId != 0) {
      Optional<ChatRoom> chatRoom1 = chatRoomRepository.findById(chatRoomId);

      ChatRoom chatRoom = chatRoom1.get();
      ProductInfo productInfo = productMapper.toProductInfo(product);

      List<Chat> chatList = chatRepository.findByChatRoomId(chatRoom.getId());

      List<ChatInfo> response = chatList.stream()
          .map(chatMapper::toChatResDtoList)
          .collect(Collectors.toList());

      String chatCreateAt = chatRoom.getCreatedAt();

      return chatMapper.toChatCreateResDto(chatRoom.getId(), productInfo, chatCreateAt, response);
    } else { // 상품 페이지에서 채팅하기를 누른 경우
      Optional<ChatRoom> chatRoom1 = chatRoomRepository.findChatRoom(productId, product.getUsers().getEmail(), buyer);

      if (!chatRoom1.isEmpty()) {
        ChatRoom chatRoom = chatRoom1.get();
        ProductInfo productInfo = productMapper.toProductInfo(product);

        List<Chat> chatList = chatRepository.findByChatRoomId(chatRoom.getId());

        List<ChatInfo> response = chatList.stream()
            .map(chatMapper::toChatResDtoList)
            .collect(Collectors.toList());

        String chatCreateAt = chatRoom.getCreatedAt();

        return chatMapper.toChatCreateResDto(chatRoom.getId(), productInfo, chatCreateAt, response);
      } else {
        ChatRoom chatRoom = chatRoomMapper.toEntity(product, product.getUsers().getEmail(), buyer);
        ChatRoom save = chatRoomRepository.save(chatRoom);

        ProductInfo productInfo = productMapper.toProductInfo(product);

        return chatMapper.toChatCreateNewResDto(save.getId(), productInfo);
      }

    }
  }


  @Transactional(readOnly = true)
  public List<ChatRoomRes> findChatRoomByUserId(String userEmail, int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());  // 1페이지부터 시작하도록
    Page<Object[]> results = chatRoomRepository.findByUserIn(userEmail, pageable);

    if (results.hasContent()) {
      return results.getContent().stream()
          .map(result -> mapToChatRoomRes(result, userEmail))
          .collect(Collectors.toList());
    } else {
      return Collections.emptyList(); // 채팅 리스트가 없는 경우 빈 배열을 반환
    }

  }

  private ChatRoomRes mapToChatRoomRes(Object[] result, String userEmail) {
    Long id = (Long) result[0];
    Long productId = (Long) result[1];
    String productTitle = (String) result[2];
    String productLocation = (String) result[3];
    int productPrice = (int) result[4];
    String productThumbnail = (String) result[5];
    String sellerEmail = (String) result[6];
    String buyerEmail = (String) result[7];
    String createdAt = (String) result[8];

    String chatPartnerEmail = userEmail.equals(sellerEmail) ? buyerEmail : sellerEmail;
    String chatPartnerName = userRepository.findUserByEmail(chatPartnerEmail)
        .orElseThrow(UserNotFoundException::new)
        .getName();

    String currentAt = chatRepository.findCurrentAtByChatRoomId(id)
        .stream()
        .findFirst()
        .orElse(null);

    return ChatRoomRes.builder()
        .id(id)
        .productId(productId)
        .productTitle(productTitle)
        .productLocation(productLocation)
        .currentChatAt(currentAt)
        .productPrice(productPrice)
        .productThumbnail(productThumbnail)
        .chatPartnerName(chatPartnerName)
        .createdAt(createdAt)
        .build();
  }

}
