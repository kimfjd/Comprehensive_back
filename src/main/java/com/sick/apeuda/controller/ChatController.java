//ChatController.java
package com.sick.apeuda.controller;

import com.sick.apeuda.dto.ChatMsgDto;
import com.sick.apeuda.dto.ChatRoomDto;
import com.sick.apeuda.entity.ChatMsg;
import com.sick.apeuda.entity.ChatRoom;
import com.sick.apeuda.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/room")
    public ResponseEntity<ChatRoomDto> createRoom(@RequestBody ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = chatService.createRoom(chatRoomDto.getRoomName());
        chatRoomDto.setRoomId(String.valueOf(chatRoom.getRoomId()));
        return ResponseEntity.ok(chatRoomDto);
    }

    @GetMapping("/room/{roomName}")
    public ResponseEntity<ChatRoomDto> getRoomByName(@PathVariable String roomName) {
        Optional<ChatRoom> chatRoom = chatService.findRoomByName(roomName);
        if (chatRoom.isPresent()) {
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setRoomId(String.valueOf(chatRoom.get().getRoomId()));
            chatRoomDto.setRoomName(chatRoom.get().getRoomName());
            return ResponseEntity.ok(chatRoomDto);
        } else {
            log.error("Room with name {} not found", roomName);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/room/{roomId}/messages")
    public ResponseEntity<List<ChatMsgDto>> getMessages(@PathVariable String roomId) {
        List<ChatMsg> messages = chatService.getMessages(roomId);
        List<ChatMsgDto> messageDtos = messages.stream().map(msg -> {
            ChatMsgDto dto = new ChatMsgDto();
            dto.setSender(msg.getSender());
            dto.setContent(msg.getContent());
            dto.setRoomId(msg.getRoomId());
            dto.setTimestamp(msg.getTimestamp());
            dto.setType(msg.getType()); // assuming all messages are TALK type
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(messageDtos);
    }

    @PostMapping("/room/{roomId}/messages")
    public ResponseEntity<ChatMsgDto> sendMessage(@PathVariable String roomId, @RequestBody ChatMsgDto chatMsgDto) {
        chatMsgDto.setRoomId(roomId);
        chatService.sendMessage(chatMsgDto);
        return ResponseEntity.ok(chatMsgDto);
    }
}
