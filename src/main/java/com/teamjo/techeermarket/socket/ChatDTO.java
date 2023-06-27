package com.teamjo.techeermarket.socket;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class ChatDTO {
    public enum MessageType{
        ENTER,TALK
    }

    private Long productId;
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;
}
