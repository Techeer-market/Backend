package com.teamjo.techeermarket.domain.chats.repository;

import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  @Query("SELECT c.id,"
      + " c.products.id, "
      + "c.products.title, "
      + "c.products.location, "
      + "c.products.price, "
      + "c.products.thumbnail, "
      + "c.sellerEmail, "
      + "c.buyerEmail, "
      + "c.createdAt "
      + "FROM ChatRoom c WHERE (:userEmail LIKE c.buyerEmail OR :userEmail LIKE c.sellerEmail) AND c.isDelete = false")
  Page<Object[]> findByUserIn(@Param("userEmail") String userEmail, Pageable pageable);

  @Query("SELECT c FROM ChatRoom c where (c.products.id = :productId AND c.buyerEmail LIKE :userEmail1 AND c.sellerEmail LIKE :userEmail2) OR (c.products.id = :productId AND c.buyerEmail LIKE :userEmail2 AND c.sellerEmail LIKE :userEmail1)")
  Optional<ChatRoom> findChatRoom(@Param("productId") Long productId, @Param("userEmail1") String userEmail1, @Param("userEmail2") String userEmail2);
}
