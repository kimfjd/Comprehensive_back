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
@Table(name = "billing_keys")
public class BillingKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billing_key_id")
    private Long billingKeyId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "customer_uid", nullable = false)
    private String customerUid; // 포트원에서 발급한 고객 UID (고유 결제 식별자)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;// 빌링키 생성 시간


}