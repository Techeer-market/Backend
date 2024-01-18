package com.teamjo.techeermarket.socket.repository;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.socket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    void deleteByProducts(Products products);   // 상품 삭제시 채팅방 삭제

    @Query(" select r from ChatRoom r where r.buyer.email= :email or r.products.users.email= :email")     //  buyer의 이메일과 일치하거나, 판매자의 이메일과 일치하는 경우
    List<ChatRoom> findChatRoomByUser (@Param("email") String email);
    Optional<ChatRoom> findByProductsAndBuyerId(Long productId, Long buyerId);
    Optional<ChatRoom> findByChatRoomName (String chatRoomName);
    List<ChatRoom> findByProductsId (Long productId);


}
