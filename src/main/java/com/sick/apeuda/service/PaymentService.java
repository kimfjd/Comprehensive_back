package com.sick.apeuda.service;

import com.sick.apeuda.dto.*;
import com.sick.apeuda.entity.*;
import com.sick.apeuda.repository.*;

import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
            paymentHistory.setName(paymentHistoryDto.getName());
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
    public List<PaymentHistoryDto> getHistory(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다"));
        List<PaymentHistory> paymentHistories = paymentHistoryRepository.findByMember(member);
        if (paymentHistories.isEmpty()) {
            throw new RuntimeException("결제 내역이 존재하지 않습니다");
        }
        return paymentHistories.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private PaymentHistoryDto convertEntityToDto(PaymentHistory paymentHistory) {
        PaymentHistoryDto paymentHistoryDto = new PaymentHistoryDto();
        paymentHistoryDto.setMember(paymentHistory.getMember().getEmail());
        paymentHistoryDto.setPaymentHistoryId(paymentHistory.getPaymentHistoryId());
        paymentHistoryDto.setPaymentDate(paymentHistory.getPaymentDate());
        paymentHistoryDto.setName(paymentHistory.getName());
        paymentHistoryDto.setAmount(paymentHistory.getAmount());
        return paymentHistoryDto;
    }
    public List<SubscriptionDto> getdeadline(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다"));
        List<Subscription> subscriptions= subscriptionRepository.findByMember(member);
        if(subscriptions.isEmpty()){
            throw new RuntimeException("구독 정보가 존재하지 않습니다. 구독하여 아프다를 이용해보세요");
        }
        return subscriptions.stream().map(this::convertEntityToDto1).collect(Collectors.toList());
    }
    private SubscriptionDto convertEntityToDto1(Subscription subscription){
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setValidUntil(subscription.getValidUntil());
        return subscriptionDto;
    }
}