package com.sick.apeuda.dto;

import com.sick.apeuda.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ApplyReqDto {
    private Long applyId;
    private Member applicant;
    private Long projectId;
    private Boolean applyStatus;
    private LocalDateTime applyTime;
    //private String managerEmail;

}
