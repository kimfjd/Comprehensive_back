package com.sick.apeuda.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardDto {
    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private String projectName;
    private String img;
    private String userName; // User 엔티티의 이름만 포함
    // ReplyDto 리스트 이게 필요한가 한번더 보기 -> 반대로 replyDto에서 boardId를 받아야 해당 댓글이 어느 게시판 댓글인지 알수있을거같음
    private List<ReplyDto> replies;
}