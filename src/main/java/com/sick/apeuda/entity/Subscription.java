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
@Table(name = "Subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long subscriptionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "billing_key_id", nullable = false)
    private BillingKey billingKey; // 정기 결제를 위한 빌링키 (고객 UID)


    @Column(name = "transaction_id", nullable = false)
    private String transactionId; //결제 트랜잭션 ID

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate; //결제가 이루어진 날짜와 시간

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; //구독 정보 생성 시간

}



