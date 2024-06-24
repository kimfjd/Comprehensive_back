package com.sick.apeuda.dto;

import com.sick.apeuda.entity.Skill;
import com.sick.apeuda.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProjectDto {
    private Long projectId;
    private String job;
    private String projectName;
    private String projectPassword;
    private LocalDateTime projectTime;
    private String userId;
    private List<Skill> skillName;
}
