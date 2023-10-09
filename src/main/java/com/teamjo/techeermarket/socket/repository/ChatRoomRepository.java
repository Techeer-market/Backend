package com.teamjo.techeermarket.socket.repository;

import com.teamjo.techeermarket.socket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    Optional<ChatRoom> findByChatRoomName(String name);
}
