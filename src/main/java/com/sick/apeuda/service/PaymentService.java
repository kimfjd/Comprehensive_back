package com.sick.apeuda.service;

import com.sick.apeuda.dto.PaymentHistoryDto;
import com.sick.apeuda.dto.PaymentInfoDto;
import com.sick.apeuda.dto.SubscriptionDto;
import com.sick.apeuda.entity.*;
import com.sick.apeuda.repository.*;

import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public boolean savePaymentHistory(PaymentHistoryDto paymentHistoryDto) {
        try {
            String userId = getCurrentMemberId(); // JWT 토큰에서 사용자 ID를 추출합니다.
            User user = userRepository.findById("kimfjd")
                    .orElseThrow(() -> new RuntimeException("User not found"));

            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setUser(user);
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
            String userId = getCurrentMemberId(); // JWT 토큰에서 사용자 ID를 추출합니다.
            User user = userRepository.findById("kimfjd")
                    .orElseThrow(() -> new RuntimeException("User not found"));
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setUser(user);
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

    public boolean saveSubscriptions(SubscriptionDto subscriptionDto){
        try {
            String userId = getCurrentMemberId(); // JWT 토큰에서 사용자 ID를 추출합니다.
            User user = userRepository.findById("kimfjd")
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Subscription subscription = new Subscription();
            subscription.setUser(user);
            subscription.setCustomerUid(subscriptionDto.getCustomerUid());
            subscription.setTransactionId(subscriptionDto.getTransactionId());
            subscription.setPaymentDate(subscriptionDto.getPaymentDate());
            subscription.setCreatedAt(subscriptionDto.getCreatedAt());
            subscription.setValidUntil(subscriptionDto.getValidUntil());
            subscription.setStatus(subscriptionDto.getStatus());
            subscription.setBillingKeyCreatedAt(subscriptionDto.getBillingKeyCreatedAt());

            subscriptionRepository.save(subscription);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}