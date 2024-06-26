package com.sick.apeuda.dto;

import com.sick.apeuda.entity.Skill;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProjectResDto {
    private Long projectId;
    private String job;
    private String projectName;
    private String projectTitle;
    //private String projectPassword;
    private LocalDateTime projectTime;
    private LocalDateTime regDate;
    private String email;
    private String projectContent;
    private String nickName;
    private String profileImg;
    private String recruitNum;
    private String recruitMember;
    private List<Skill> skillName;
    private List<ReplyDto> replies;
}
