package com.teamjo.techeermarket.socket;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService service;
    @PostMapping
    public ChatRoom createRoom(@RequestParam String email,@RequestParam("productUuid") String productUuidString){
        log.info("Uuid :: {}",productUuidString);
        UUID productUuId = UUID.fromString(productUuidString);
        log.info("email :: {}",email);
        return service.createRoom(email,productUuId);
    }

    @GetMapping
    public List<ChatRoom> findAllRooms(){
        return service.findAllRoom();
    }


}
