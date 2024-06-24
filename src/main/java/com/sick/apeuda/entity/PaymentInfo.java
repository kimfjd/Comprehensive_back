package com.sick.apeuda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "PaymentInfo")
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_info_id")
    private Long paymentInfoId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String paymentMethodCode;

    @Lob
    @Column(nullable = false)
    private String paymentDetails;

    @Column(nullable = false)
    private Boolean isPaymentAvailable;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column
    private LocalDateTime deletedAt;
}