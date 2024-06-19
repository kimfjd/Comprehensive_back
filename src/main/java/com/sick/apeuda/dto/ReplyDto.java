package com.sick.apeuda.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyDto {
    private Long replyId;
    private String content;
    private LocalDateTime regDate;
    private String nickName;
    private String profile_img;
    private Long boardId;
}
