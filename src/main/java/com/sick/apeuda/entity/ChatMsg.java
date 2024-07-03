// ChatMsg.java
package com.sick.apeuda.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sick.apeuda.dto.ChatMsgDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String localDateTime ;

    @Enumerated(EnumType.STRING)
    private ChatMsgDto.MessageType type; // MessageType 열거형 사용
    @PrePersist // 날짜가 비어있는경우 현재 시간 자동 입력
    protected void onCreate() {
        if (this.localDateTime == null) {
            this.localDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        }
    }
}
