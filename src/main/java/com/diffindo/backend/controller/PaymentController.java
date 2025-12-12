package com.diffindo.backend.controller;

import com.diffindo.backend.dto.*;
import com.diffindo.backend.exceptions.BadTokenException;
import com.diffindo.backend.repository.PaymentRepository;
import com.diffindo.backend.service.payment.PaymentStatusService;
import com.diffindo.backend.util.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/decision")
    public ResponseEntity<PaymentDecisionResponseDto> approveOrDecline(
            @RequestBody PaymentDecisionRequestDto paymentDecisionRequestDto,
            @RequestHeader("Authorization") String authToken
    ) {
        logger.info("extracting username from token");
        String[] tokenParts = authToken.split(" ");
        if (tokenParts.length != 2) {
            logger.info("invalid token");
            throw new BadTokenException("bad authentication token");
        }
        String username = jwtUtil.extractUsername(tokenParts[1]);

        logger.info("initiating payment status update");
        return ResponseEntity.ok(paymentStatusService.executePaymentDecision(paymentDecisionRequestDto, username));
    }

    @GetMapping("/fetch")
    public ResponseEntity<PaymentFetchResponseDto> fetchAll(
            @RequestHeader("Authorization") String authToken
    ) {
        logger.info("extracting username from token");
        String[] tokenParts = authToken.split(" ");
        if (tokenParts.length != 2) {
            logger.info("invalid token when requesting to fetch all payments");
            throw new BadTokenException("bad authentication token");
        }
        String username = jwtUtil.extractUsername(tokenParts[1]);

        logger.info("initiating fetch of all payments for user");
        return ResponseEntity.ok(paymentStatusService.fetchAllPayments(username));
    }

}
