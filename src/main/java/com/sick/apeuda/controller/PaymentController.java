package com.sick.apeuda.controller;

import com.sick.apeuda.dto.PaymentHistoryDto;
import com.sick.apeuda.dto.PaymentInfoDto;
import com.sick.apeuda.dto.SubscriptionDto;
import com.sick.apeuda.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("/info")
    public ResponseEntity<Boolean> savePaymentinfo(@RequestBody PaymentInfoDto paymentInfoDto){
        boolean isTrue=paymentService.savePaymentinfo(paymentInfoDto);
        return ResponseEntity.ok(isTrue);
    }

    @PostMapping("subscriptions")
    public ResponseEntity<Boolean> saveSubscriptions(@RequestBody SubscriptionDto subscriptionDto){
        boolean isTrue=paymentService.saveSubscriptions(subscriptionDto);
        return ResponseEntity.ok(isTrue);
    }
}