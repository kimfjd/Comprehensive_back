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
    private ChatMsgDto.MessageType type; // MessageType 열거형 사용
    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = new Timestamp(System.currentTimeMillis());
        }
    }
}
