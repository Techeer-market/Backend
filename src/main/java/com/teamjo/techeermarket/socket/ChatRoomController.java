package com.teamjo.techeermarket.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat-room")
public class ChatRoomController {
    private final ChatService chatService;

    @GetMapping
    public List<ChatRoom> getChatRoom(int userId){
        return chatService.findAllRoom(userId);
    }

    @PostMapping("/create")
    public ChatRoom createRoom(int userId, String roomName){
        return chatService.createChatRoom(userId,roomName);
    }

    @GetMapping("/enter")
    public ChatRoom findRoomById(String roomId){
        return chatService.findRoomById(roomId);
    }

}
