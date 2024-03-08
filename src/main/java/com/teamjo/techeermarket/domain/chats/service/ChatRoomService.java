package com.teamjo.techeermarket.domain.chats.service;


import com.teamjo.techeermarket.domain.chats.dto.response.ChatCreateRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRoomRes;
import com.teamjo.techeermarket.domain.chats.dto.response.ProductInfo;
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
  public ChatCreateRes createChatRoom(Long productId, String buyer) {

    Products product = productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);;

    ChatRoom chatRoom = chatRoomMapper.toEntity(product, product.getUsers().getEmail(), buyer);
    ChatRoom save = chatRoomRepository.save(chatRoom);

    ProductInfo productInfo = productMapper.toProductInfo(product);

    return chatMapper.toChatCreateResDto(save.getId(), productInfo);
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
        .build();
  }

}
