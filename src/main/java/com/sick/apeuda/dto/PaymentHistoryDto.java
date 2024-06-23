package com.sick.apeuda.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentHistoryDto {
    private Long id;
    private String email;
    private LocalDateTime paymentDate;
    private String paymentStatus;
    private String transactionId;
    private Long amount;
}