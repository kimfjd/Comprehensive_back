package com.sick.apeuda.controller;


import com.sick.apeuda.dto.ChatMsgDto;
import com.sick.apeuda.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {
    private final ChatService chatService;

    public WebSocketChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMsgDto sendMessage(ChatMsgDto chatMsgDto) {
        chatService.sendMessage(chatMsgDto); // 메시지를 서비스 레이어로 전달하여 저장
        return chatMsgDto; // WebSocket을 통해 모든 구독자에게 메시지 전달
    }
}
