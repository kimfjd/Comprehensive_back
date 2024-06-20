package com.sick.apeuda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomReqDto {
    private String email;
    private String name; // 채팅방 이름
}