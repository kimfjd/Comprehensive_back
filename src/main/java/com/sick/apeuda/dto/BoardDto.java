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
    private String nickName;
    private String profile_img;
    private List<ReplyDto> replies;
}