package com.teamjo.techeermarket.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    public enum MessageType{
        ENTER,TALK,LEAVE
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private String time;
}
