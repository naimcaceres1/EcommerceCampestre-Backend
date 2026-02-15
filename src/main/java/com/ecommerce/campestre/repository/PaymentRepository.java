package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByTransactionRef(String transactionRef);
}
