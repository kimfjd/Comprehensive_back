package com.sick.apeuda.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReadMessageDto {
    private String member1;
    private String member2;
    private String content;
    private Boolean readCheck;
    
}
