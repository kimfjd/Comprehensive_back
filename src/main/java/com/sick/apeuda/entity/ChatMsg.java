// ChatMsg.java
package com.sick.apeuda.entity;

import com.sick.apeuda.dto.ChatMsgDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ChatMsg")
public class ChatMsg {
    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatId;

    private String sender;
    private String content;
    private String roomId;
    private Timestamp timestamp;
    @Enumerated(EnumType.STRING)
    private ChatMsgDto.MessageType type; // 채팅방에서 유저 상태관리하기 위한 채팅 타입 필드추가 입장/ 대화/ 퇴장
}
