package com.teamjo.techeermarket.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * setApplicationDestinationPrefixes
   * - 클라이언트에서 보낸 메세지를 받을 prefix
   * - 클라이언트는 /pub/chat으로 메시지를 보내면, 이 메시지는 서버에서 처리
   *
   * enableSimpleBroker
   * - 해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달
   * - 메시지 브로커를 활성화하고, 클라이언트들이 구독(subscribe)할 수 있는 주제(topic)를 설정
   * - /sub/chat 주제로 메시지를 발송하면 해당 주제를 구독하고 있는 모든 클라이언트에게 메시지가 전송
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // 메세지 발행 요청 url -> 메세지 보낼 때
    // 클라이언트가 메시지를 보내는 목적지의 접두사
    registry.setApplicationDestinationPrefixes("/pub");
    // 서버에서 클라이언트로 메시지를 보낼 때 사용
    registry.enableSimpleBroker("/sub");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws-stomp")
        .setAllowedOriginPatterns("*") // 주소 : ws://localhost:8080/ws-stomp
        .withSockJS(); // SocketJS 를 연결한다는 설정 (버전 낮은 브라우저에서도 적용 가능)
  }


}
