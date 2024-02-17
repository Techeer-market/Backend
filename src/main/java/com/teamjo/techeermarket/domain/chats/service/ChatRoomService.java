package com.teamjo.techeermarket.domain.chats.service;


import com.teamjo.techeermarket.domain.chats.dto.request.ChatRoomCreateReq;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRoomRes;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.chats.mapper.ChatRoomMapper;
import com.teamjo.techeermarket.domain.chats.repository.ChatRoomRepository;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.exception.user.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;
  private final ProductRepository productRepository;
  private final ChatRoomMapper chatRoomMapper;
  private final UserRepository userRepository;


  public Long createChatRoom(ChatRoomCreateReq chatRoomCreateReq, String buyer) {

    Optional<Products> product = productRepository.findById(chatRoomCreateReq.getProductId());

    ChatRoom chatRoom = chatRoomMapper.toEntity(product.get(), product.get().getUsers().getEmail(), buyer);
    ChatRoom save = chatRoomRepository.save(chatRoom);

    return save.getId();
  }

  public List<ChatRoomRes> findChatRoomByUserId(String userEmail) {
    List<Object[]> results = chatRoomRepository.findByUserIn(userEmail);

    List<ChatRoomRes> chatRoomResponse = new ArrayList<>();
    for (Object[] result : results) {
      Long id = (Long) result[0];
      Long productId = (Long) result[1];
      String productTitle = (String) result[2];
      String productLocation = (String) result[3];
      int productPrice = (int) result[4];
      String productThumbnail = (String) result[5];
      String sellerEmail = (String) result[6];
      String buyerEmail = (String) result[7];

      String chatPartnerEmail = userEmail.equals(sellerEmail) ? buyerEmail : sellerEmail;
      String chatPartnerName = userRepository.findUserByEmail(chatPartnerEmail).orElseThrow(
          UserNotFoundException::new).getName();

      ChatRoomRes response = new ChatRoomRes(id, productId, productTitle, productLocation, productPrice, productThumbnail, chatPartnerName);
      chatRoomResponse.add(response);
    }

    return chatRoomResponse;
  }

}
