package com.teamjo.techeermarket.domain.chats.repository;

import com.teamjo.techeermarket.domain.chats.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
