package com.sep.sellers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.sellers.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String>{

	@SuppressWarnings("unchecked")
	Payment save(Payment payment);	
}