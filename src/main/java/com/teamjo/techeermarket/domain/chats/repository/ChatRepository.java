package com.teamjo.techeermarket.domain.chats.repository;

import com.teamjo.techeermarket.domain.chats.entity.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, Long> {

  @Query("SELECT c FROM Chat c WHERE c.chatRoom.id = :chatRoomId ORDER BY c.createdAt DESC")
  List<Chat> findByChatRoomId(@Param("chatRoomId") Long chatRoomId);
}
