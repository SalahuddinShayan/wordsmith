package com.wordsmith.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wordsmith.Entity.PaymentTransaction;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    List<PaymentTransaction> findByUsername(String username);
    List<PaymentTransaction> findByStatus(String status);

    boolean existsByPaypalOrderId(String paypalOrderId);

    Optional<PaymentTransaction> findByPaypalOrderId(String paypalOrderId);
}