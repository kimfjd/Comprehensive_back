package com.sick.apeuda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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

    @Lob
    private String message;

    private LocalDateTime sendDate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

}
