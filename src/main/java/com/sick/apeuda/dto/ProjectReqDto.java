package com.sick.apeuda.dto;

import com.sick.apeuda.entity.Skill;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProjectReqDto {
    private Long projectId;
    private String job;
    private String projectName;
    private String projectTitle;
    private String projectPassword;
    private String projectContent;
    private String imgPath;
    private LocalDateTime projectTime;
    private LocalDateTime regDate;
    private int recruitNum;

    private String email;
    private String nickName;
    private String profileImg;
    private List<Skill> skillName;
}
