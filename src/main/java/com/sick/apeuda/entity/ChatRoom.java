// ChatRoom.java
package com.sick.apeuda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ChatRoom")
public class ChatRoom {
    @Id
    @Column(name = "room_id")
    private String roomId = UUID.randomUUID().toString(); // UUID를 사용한 무작위 방아이디 생성

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(unique = true)
    private String roomName; // 방 이름
    private Integer currentCount;
    private Integer maxCount;
    private String status;
    private String roomPassword;

}
