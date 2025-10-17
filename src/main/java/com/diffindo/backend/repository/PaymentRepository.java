package com.diffindo.backend.repository;

import com.diffindo.backend.model.Group;
import com.diffindo.backend.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payments, Long> {
    Optional<Group> findByPaymentId(Long paymentId);
}
