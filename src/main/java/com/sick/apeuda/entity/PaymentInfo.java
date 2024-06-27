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
    private String paymentMethodCode; // 신용카드, 은행송금, 구글페이 등등

    @Lob
    @Column(nullable = false)
    private String paymentDetails;  // 구독에 관한 모든 데이터

    @Column(nullable = false)
    private Boolean isPaymentAvailable; // 결제의 성공 여부

    @Column(nullable = false)
    private Boolean isDeleted; // 사용자가 결제 정보 삭제를 하면 이 컬럼이 false에서 True로 바뀌고 실제로 결제정보를 삭제하지는 않음

    @Column
    private LocalDateTime deletedAt; // 삭제 정보 일시
}