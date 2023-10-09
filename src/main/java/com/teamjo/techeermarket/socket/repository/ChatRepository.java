package com.teamjo.techeermarket.socket.repository;

import com.teamjo.techeermarket.socket.entity.Chat;
import com.teamjo.techeermarket.socket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findByChatRoom(ChatRoom chatRoom);
}
