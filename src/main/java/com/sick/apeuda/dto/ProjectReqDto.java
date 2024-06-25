package com.sick.apeuda.dto;

import com.sick.apeuda.entity.Skill;
import com.sick.apeuda.entity.User;
import lombok.Getter;
import lombok.Setter;

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
    // Data(sql)로 바꿔야될듯
    private LocalDateTime projectTime;
    private LocalDateTime regDate;
    private String email;
    private String projectContent;
    private String nickName;
    private String profileImg;
    private List<Skill> skillName;
}
