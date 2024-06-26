package com.sick.apeuda.service;

import com.sick.apeuda.dto.PaymentHistoryDto;
import com.sick.apeuda.dto.PaymentInfoDto;
import com.sick.apeuda.dto.SubscriptionDto;
import com.sick.apeuda.entity.*;
import com.sick.apeuda.repository.*;

import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;

    public boolean savePaymentHistory(PaymentHistoryDto paymentHistoryDto) {
        try {
            String memberId = getCurrentMemberId(); // JWT 토큰에서 사용자 ID를 추출합니다.
            Member member = memberRepository.findById("kimfjd")
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setMember(member);
            paymentHistory.setPaymentDate(paymentHistoryDto.getPaymentDate());
            paymentHistory.setPaymentStatus(paymentHistoryDto.getPaymentStatus());
            paymentHistory.setTransactionId(paymentHistoryDto.getTransactionId());
            paymentHistory.setAmount(paymentHistoryDto.getAmount());
            paymentHistory.setCancellationDate(paymentHistoryDto.getCancellationDate());

            paymentHistoryRepository.save(paymentHistory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean savePaymentinfo(PaymentInfoDto paymentInfoDto) {
        try {
            String memberId = getCurrentMemberId(); // JWT 토큰에서 사용자 ID를 추출합니다.
            Member member = memberRepository.findById("kimfjd")
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setMember(member);
            paymentInfo.setPaymentMethodCode(paymentInfoDto.getPaymentMethodCode());
            paymentInfo.setPaymentDetails(paymentInfoDto.getPaymentDetails());
            paymentInfo.setIsPaymentAvailable(paymentInfoDto.getIsPaymentAvailable());
            paymentInfo.setIsDeleted(paymentInfoDto.getIsDeleted());

            paymentInfoRepository.save(paymentInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveSubscriptions(SubscriptionDto subscriptionDto) {
        try {
            String memberId = getCurrentMemberId(); // JWT 토큰에서 사용자 ID를 추출합니다.
            Member member = memberRepository.findById("kimfjd")
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            // member_id로 기존 구독 정보 조회
            Subscription subscription = subscriptionRepository.findByMemberId("kimfjd");
            System.out.println("subscription 값은 : "+subscription);
            if (subscription == null) {
                // 새로운 구독 정보 생성
                subscription = new Subscription();
                subscription.setMember(member);
                subscription.setCustomerUid(subscriptionDto.getCustomerUid());
                subscription.setTransactionId(subscriptionDto.getTransactionId());
                subscription.setPaymentDate(subscriptionDto.getPaymentDate());
                subscription.setCreatedAt(subscriptionDto.getCreatedAt());
                subscription.setValidUntil(subscriptionDto.getValidUntil());
                subscription.setStatus(subscriptionDto.getStatus());
                subscription.setBillingKeyCreatedAt(subscriptionDto.getBillingKeyCreatedAt());
                System.out.println("subscription insert : "+subscription);
                subscriptionRepository.save(subscription);

            } else {
                // 기존 구독 정보 업데이트
                subscription.setCustomerUid(subscriptionDto.getCustomerUid());
                subscription.setTransactionId(subscriptionDto.getTransactionId());
                subscription.setPaymentDate(subscriptionDto.getPaymentDate());
                subscription.setCreatedAt(subscriptionDto.getCreatedAt());
                subscription.setValidUntil(subscriptionDto.getValidUntil());
                subscription.setStatus(subscriptionDto.getStatus());
                subscription.setBillingKeyCreatedAt(subscriptionDto.getBillingKeyCreatedAt());
                System.out.println("subscription update : "+subscription);
                subscriptionRepository.save(subscription);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}