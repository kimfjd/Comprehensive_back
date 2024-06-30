//WebSocketConfiguration.java
package com.sick.apeuda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // 스프링부트 설정관련 클래스 선언
@EnableWebSocketMessageBroker // WebSocket 메시지브로커 활성화 선언
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer { // 메시지 브로커 설정 메서드를 제공하는 인터페이스 구현의무 부여

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) { //
        config.enableSimpleBroker("/topic", "/queue"); // 메시지 브로커를 활성화하여 구독사항에 맞춰 메시지 전달 /topic: 단체채팅용 /queue: 특정 사람에게만 전달
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트에서 채팅보낼때 접두사 설정
        config.setUserDestinationPrefix("/user"); // 메시지 보낼 사용자 지정 할때 접두사 설정
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { //웹소켓 통신 엔트포인트(접속주소) 등록
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000").withSockJS();
    }
}