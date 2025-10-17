package com.diffindo.backend.controller;

import com.diffindo.backend.dto.*;
import com.diffindo.backend.service.payment.PaymentStatusService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentStatusService paymentStatusService;

    @PostMapping("/decision")
    public ResponseEntity<PaymentDecisionResponseDto> approveOrDecline(@RequestBody PaymentDecisionRequestDto paymentDecisionRequestDto) {
        logger.info("initiating payment status update");
        return ResponseEntity.ok(paymentStatusService.executePaymentDecision(paymentDecisionRequestDto));
    }

    @GetMapping("/fetch")
    public ResponseEntity<PaymentFetchResponseDto> fetchAll(@RequestBody PaymentFetchRequestDto paymentFetchRequestDto) {
        logger.info("initiating fetch of all payments for user");
        return ResponseEntity.ok(paymentStatusService.fetchAllPayments(paymentFetchRequestDto));
    }

}
