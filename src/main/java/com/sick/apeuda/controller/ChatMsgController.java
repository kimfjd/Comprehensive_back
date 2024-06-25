package com.sick.apeuda.controller;

import com.sick.apeuda.dto.ChatMsgDto;
import com.sick.apeuda.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMsgController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatMsgController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMsgDto chatMessageDto) {
        chatService.sendMessage(chatMessageDto);
        messagingTemplate.convertAndSend("/topic/room/" + chatMessageDto.getRoomId(), chatMessageDto);
    }
}