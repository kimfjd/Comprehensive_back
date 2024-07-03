// ChatService.java
package com.sick.apeuda.service;

import com.sick.apeuda.dto.ChatMsgDto;
import com.sick.apeuda.dto.ProjectReqDto;
import com.sick.apeuda.entity.ChatMsg;
import com.sick.apeuda.entity.ChatRoom;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.Project;
import com.sick.apeuda.repository.ChatMsgRepository;
import com.sick.apeuda.repository.ChatRoomRepository;
import com.sick.apeuda.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;
import java.util.UUID;

@Service
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMsgRepository chatMsgRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, ChatMsgRepository chatMsgRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMsgRepository = chatMsgRepository;
    }

    public ChatRoom createRoom(String roomName) {
        ChatRoom chatRoom = new ChatRoom();
        ProjectReqDto project = new ProjectReqDto();

        chatRoom.setRoomName(roomName);

        return chatRoomRepository.save(chatRoom);
    }

    public Optional<ChatRoom> findRoomByName(String roomName) {
        return chatRoomRepository.findByRoomName(roomName);
    }
    public List<ChatMsg> getMessages(String roomId) {
        return chatMsgRepository.findByRoomId(roomId);
    }

    public void sendMessage(ChatMsgDto chatMessageDto) {
        ChatMsg chatMessage = new ChatMsg();
        chatMessage.setSender(chatMessageDto.getSender());
        chatMessage.setContent(chatMessageDto.getContent());
        chatMessage.setRoomId(chatMessageDto.getRoomId());
        chatMessage.setType(chatMessageDto.getType());
        chatMessage.setLocalDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)); // 현재 시간 설정
        chatMsgRepository.save(chatMessage);
    }
}
