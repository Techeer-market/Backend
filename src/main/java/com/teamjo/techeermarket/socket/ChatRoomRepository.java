package com.teamjo.techeermarket.socket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findChatRoomByProductUuId(UUID productUuid);
    boolean existsByProductUuId(UUID productUuid);

}
