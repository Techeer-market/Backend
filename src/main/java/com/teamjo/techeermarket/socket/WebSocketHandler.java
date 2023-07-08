package com.teamjo.techeermarket.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{

        String payload = message.getPayload();
        log.info("handleTextMessage :: payload :: {}", payload);
        log.info("handleTextMessage :: session :: {}", session);

        ChatMessage chatMessage = mapper.readValue(payload, ChatMessage.class);
        log.info("handlerTextMessage :: chatDTO :: {}", chatMessage.toString());
        ChatRoom room = chatService.findRoomByProductId(chatMessage.getProductUuid());
        chatMessage.setChatRoom(room);
        chatService.updateChatRoom(chatMessage,chatMessage.getProductUuid());
        log.info("handlerTextMessage :: room :: {}", room.toString());
        room.handleAction(session, chatMessage, chatService);
    }
}
