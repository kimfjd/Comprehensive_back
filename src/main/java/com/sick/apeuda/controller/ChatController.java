//ChatController.java
package com.sick.apeuda.controller;

import com.sick.apeuda.dto.ChatMsgDto;
import com.sick.apeuda.dto.ChatRoomDto;
import com.sick.apeuda.entity.ChatMsg;
import com.sick.apeuda.entity.ChatRoom;
import com.sick.apeuda.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createRoom(@RequestBody Map<String, String> request, Authentication authentication) {
        String roomName = request.get("roomName");
        String memberId = authentication.getName(); // 인증된 사용자 ID 가져오기
        ChatRoom chatRoom = chatService.createRoom(roomName, memberId);
        return ResponseEntity.ok(chatRoom);
    }
    @PostMapping("/join-to-room/{roomId}") // *** 채팅방 id가 입력되면 해당 유저가 참가 됨
    public ResponseEntity<Void> joinRoom(@PathVariable String roomId, Authentication authentication) {
        chatService.joinRoom(roomId, authentication.getName());
        return ResponseEntity.ok().build();
    }

    // **** 중요 내가 ChatManage DB에 참여하고 있는지 확인하여 Project DB를 반환 ChatManage 반환이 아님
    @GetMapping("/find-my-room")
    public ResponseEntity<List<ChatRoom>> getRoomList(Authentication authentication) {
        List<ChatRoom> enteredRooms = chatService.getJoinedRooms(authentication.getName());
        return ResponseEntity.ok(enteredRooms);
    }

    @GetMapping("/find-room/{roomName}") // 방이름 입력받아 방리스트에서 방 조회
    public ResponseEntity<ChatRoomDto> findRoomByRoomName(@PathVariable String roomName) {
        Optional<ChatRoom> chatRoom = chatService.findRoomByRoomName(roomName);
        if (chatRoom.isPresent()) {
            ChatRoomDto chatRoomDto = new ChatRoomDto();
            chatRoomDto.setRoomId(chatRoom.get().getRoomId());
            chatRoomDto.setRoomName(chatRoom.get().getRoomName());
            return ResponseEntity.ok(chatRoomDto);
        } else {
            log.error("Room with name {} not found", roomName);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/exit/{roomId}")
    public ResponseEntity<Void> exitRoom(@PathVariable String roomId, Authentication authentication) {
        String memberId = authentication.getName();
        chatService.exitRoom(roomId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<ChatMsgDto>> getMessages(@PathVariable String roomId) {
        List<ChatMsg> messages = chatService.getMessages(roomId);
        List<ChatMsgDto> messageDtos = messages.stream().map(msg -> {
            ChatMsgDto dto = new ChatMsgDto();
            dto.setSenderId(msg.getSender().getEmail());
            dto.setContent(msg.getContent());
            dto.setRoomId(msg.getChatRoom().getRoomId());
            dto.setLocalDateTime(msg.getLocalDateTime());
            dto.setType(msg.getType());
            dto.setProfileImgPath(msg.getSender().getProfileImgPath());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(messageDtos);
    }
}
