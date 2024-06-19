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
    private String userName;
    // 확인해보고 필요할경우 주석 풀기
    //private Long boardId;
}
