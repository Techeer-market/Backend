package com.teamjo.techeermarket.socket;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ChatDTO {
    public enum MessageType{
        ENTER,TALK
    }

    private UUID productUuid;
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;
}
