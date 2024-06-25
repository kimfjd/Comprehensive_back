package com.sick.apeuda.controller;

import com.sick.apeuda.dto.PaymentHistoryDto;
import com.sick.apeuda.entity.PaymentHistory;
import com.sick.apeuda.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/save")
    public ResponseEntity<Boolean> savePaymentHistory(@RequestBody PaymentHistoryDto paymentHistoryDto) {
        boolean isTrue = paymentService.savePaymentHistory(paymentHistoryDto);
        return ResponseEntity.ok(isTrue);
    }
}