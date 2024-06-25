package com.sick.apeuda.service;

import com.sick.apeuda.dto.PaymentHistoryDto;
import com.sick.apeuda.entity.PaymentHistory;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.repository.PaymentHistoryRepository;
import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;

import com.sick.apeuda.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentHistoryRepository paymentHistoryRepository;
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
}