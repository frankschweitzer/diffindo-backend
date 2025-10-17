package com.diffindo.backend.repository;

import com.diffindo.backend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(Long paymentId);
    Optional<Payment> findPaymentByGroupIdAndUserId(Long groupId, Long userId);
    Optional<List<Payment>> findAllByUserId(Long userId);
}
