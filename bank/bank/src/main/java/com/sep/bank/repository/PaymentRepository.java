package com.sep.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.bank.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
