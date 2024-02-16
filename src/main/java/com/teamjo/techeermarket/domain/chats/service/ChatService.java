package com.teamjo.techeermarket.domain.chats.service;

import com.teamjo.techeermarket.domain.chats.dto.request.ChatReq;
import com.teamjo.techeermarket.domain.chats.entity.Chat;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.chats.mapper.ChatMapper;
import com.teamjo.techeermarket.domain.chats.repository.ChatRepository;
import com.teamjo.techeermarket.domain.chats.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatRoomRepository chatRoomRepository;
  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;

  public void saveMessage(ChatReq chatReq) {
    ChatRoom chatRoom = chatRoomRepository.findById(chatReq.getChatRoomId()).orElseThrow();

    Chat chat = chatMapper.toEntity(chatRoom, chatReq.getSenderEmail(), chatReq.getMessage());

    chatRepository.save(chat);
  }
}
