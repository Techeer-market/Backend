package com.teamjo.techeermarket.domain.chats.repository;

import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import java.util.List;
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
      + "c.buyerEmail "
      + "FROM ChatRoom c WHERE :userEmail LIKE c.buyerEmail OR :userEmail LIKE c.sellerEmail")
  List<Object[]> findByUserIn(@Param("userEmail") String userEmail);
}
