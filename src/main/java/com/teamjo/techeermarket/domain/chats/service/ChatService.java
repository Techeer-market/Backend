package com.teamjo.techeermarket.domain.chats.service;

import com.teamjo.techeermarket.domain.chats.dto.request.ChatReq;
import com.teamjo.techeermarket.domain.chats.dto.response.ChatRes;
import com.teamjo.techeermarket.domain.chats.entity.Chat;
import com.teamjo.techeermarket.domain.chats.entity.ChatRoom;
import com.teamjo.techeermarket.domain.chats.mapper.ChatMapper;
import com.teamjo.techeermarket.domain.chats.repository.ChatRepository;
import com.teamjo.techeermarket.domain.chats.repository.ChatRoomRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatRoomRepository chatRoomRepository;
  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;

  @Transactional
  public void saveMessage(ChatReq chatReq) {
    ChatRoom chatRoom = chatRoomRepository.findById(chatReq.getChatRoomId()).orElseThrow();

    Chat chat = chatMapper.toEntity(chatRoom, chatReq.getSenderEmail(), chatReq.getMessage());

    chatRepository.save(chat);
  }

  @Transactional(readOnly = true)
  public List<ChatRes> getMessage(Long chatRoomId) {
    List<Chat> chatList = chatRepository.findByChatRoomId(chatRoomId);
    List<ChatRes> response = chatList.stream()
        .map(chatMapper::toChatResDtoList)
        .collect(Collectors.toList());

    return response;
  }
}
