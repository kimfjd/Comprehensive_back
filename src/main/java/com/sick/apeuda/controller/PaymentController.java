package com.sick.apeuda.controller;

import com.sick.apeuda.dto.PaymentInfoDto;
import com.sick.apeuda.dto.PaymentHistoryDto;
import com.sick.apeuda.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/info")
    public ResponseEntity<String> savePaymentInfo(@RequestBody PaymentInfoDto paymentInfoDto) {
        paymentService.savePaymentInfo(paymentInfoDto);
        return ResponseEntity.ok("Payment info saved successfully");
    }

    @PostMapping("/history")
    public ResponseEntity<String> savePaymentHistory(@RequestBody PaymentHistoryDto paymentHistoryDto) {
        paymentService.savePaymentHistory(paymentHistoryDto);
        return ResponseEntity.ok("Payment history saved successfully");
    }
}