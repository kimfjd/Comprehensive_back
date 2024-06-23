package com.sick.apeuda.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentInfoDto {
    private Long id;
    private String email;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvc;
}
