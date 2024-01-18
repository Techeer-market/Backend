package com.teamjo.techeermarket.socket.service;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.socket.dto.ChatDto;
import com.teamjo.techeermarket.socket.entity.Chat;
import com.teamjo.techeermarket.socket.entity.ChatRoom;
import com.teamjo.techeermarket.socket.repository.ChatRepository;
import com.teamjo.techeermarket.socket.repository.ChatRoomRepository;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final ProductRepository productRepository;


    /**
     *  사용자의 이메일을 받아 해당 사용자가 속한 채팅방 목록 조회
     */
    @Transactional
    public List<ChatRoom> findChatRoomByUsers (String email) {
        return chatRoomRepository.findChatRoomByUser(email);
    }


    /**
     *  상품 ID와 구매자 ID를 받아서 해당 상품에 대한 채팅 방을 찾거나, 존재하지 않을 경우 새로운 빈(ChatRoom) 채팅 방을 생성
     */
    @Transactional
    public ChatRoom findChatRoomByBuyer(Long productId, Long buyerId){
        return chatRoomRepository.findByProductsAndBuyerId(productId, buyerId)
                .orElseGet(()-> new ChatRoom());
    }


    /**
     *  채팅 메시지 저장
     */
    @Transactional
    public Long saveChat(ChatDto chatDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatDto.getRoomId()).get();
        Chat chat = chatDto.toEntity(chatRoom);
        chatRepository.save(chat);
        return chat.getId();
    }


    /**
     *  해당 상품과 관련된 모든 채팅 방 조회
     */
    @Transactional
    public List<ChatRoom> findByProductId(Long productId){
        return chatRoomRepository.findByProductsId(productId);
    }


    /**
     *  해당 채팅방에 속한 모든 채팅 메시지 조회
     */
    @Transactional
    public List<Chat> findChatList(Long chatRoomId){
        return chatRepository.findByChatRoomId(chatRoomId);
    }


    /**
     *  채팅 방의 이름, 구매자, 상품ID 로 채팅방 찾거나 생성
     */
    @Transactional
    public ChatRoom findChatRoomByName(Users users, String roomName, Long productId){
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findByChatRoomName(roomName);
        Products products = productRepository.findById(productId).get();
        if(!findChatRoom.isPresent()){
            ChatRoom newChatRoom = ChatRoom.builder()
                    .chatRoomName(roomName)
                    .buyer(users)
                    .products(products)
                    .build();
            chatRoomRepository.save(newChatRoom);
            return newChatRoom;
        }
        return findChatRoom.get();
    }



}
