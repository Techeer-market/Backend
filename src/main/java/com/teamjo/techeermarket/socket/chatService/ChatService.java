package com.teamjo.techeermarket.socket.chatService;

import com.teamjo.techeermarket.socket.entity.Chat;
import com.teamjo.techeermarket.socket.entity.ChatRoom;
import com.teamjo.techeermarket.socket.repository.ChatRepository;
import com.teamjo.techeermarket.socket.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    public ChatRoom createChatRoom(String roomName){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setChatRoomName(roomName);
        return chatRoomRepository.save(chatRoom);
    }

    public Chat saveChatMessage(Chat chat){
        return chatRepository.save(chat);
    }
}
