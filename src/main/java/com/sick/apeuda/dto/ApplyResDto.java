package com.sick.apeuda.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplyResDto {
    private Long applyId;
    private String applicant;
    private Long projectId;
    private Boolean applyStatus;
    //private LocalDateTime applyTime;
    private String memberId;



}
