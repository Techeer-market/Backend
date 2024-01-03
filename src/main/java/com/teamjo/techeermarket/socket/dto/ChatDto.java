package com.teamjo.techeermarket.socket.dto;

import com.teamjo.techeermarket.socket.entity.Chat;
import com.teamjo.techeermarket.socket.entity.ChatRoom;
import com.teamjo.techeermarket.socket.entity.ChatType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatDto {
    private Long roomId;
    private ChatType type;
    private String message;

    public Chat toEntity(ChatRoom chatRoom){
        Chat chat = Chat.builder()
                .type(this.type)
                .message(this.message)
                .build();
        chat.addChat(chatRoom);
        return chat;
    }

}
