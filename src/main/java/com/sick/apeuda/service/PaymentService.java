package com.sick.apeuda.service;

import com.sick.apeuda.dto.PaymentHistoryDto;
import com.sick.apeuda.entity.PaymentHistory;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.repository.PaymentHistoryRepository;
import com.sick.apeuda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void savePaymentHistory(PaymentHistoryDto paymentHistoryDto) {
        User user = userRepository.findById(paymentHistoryDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setUser(user);
        paymentHistory.setPaymentDate(LocalDateTime.now());
        paymentHistory.setPaymentStatus(paymentHistoryDto.getPaymentStatus());
        paymentHistory.setTransactionId(paymentHistoryDto.getTransactionId());
        paymentHistory.setAmount(paymentHistoryDto.getAmount());
        paymentHistory.setCancellationDate(paymentHistoryDto.getCancellationDate());

        paymentHistoryRepository.save(paymentHistory);
    }
}