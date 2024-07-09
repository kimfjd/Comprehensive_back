// ChatRoom.java
package com.sick.apeuda.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
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

    @OneToOne // 한 프로젝트당 한 채팅방이므로
    @JoinColumn(name = "project_id")
    private Project project;


    @Column(unique = true, nullable = false)
    private String roomName; // 방 이름이 꼭 유니크 속성이어야 할까?
    private Integer currentCount;
    private Integer maxCount;
    private Boolean postType; // 게시글 종류 구분을 위한 속성 1이면 프로젝트 0이면 오픈채팅방
    //private String roomPassword;
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ChatMsg> chatMsgs;


}
