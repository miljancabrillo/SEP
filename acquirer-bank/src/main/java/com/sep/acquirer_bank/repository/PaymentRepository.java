package com.sep.acquirer_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.acquirer_bank.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Payment save(Payment pt);
	
	Payment findOneById(long id);
	
}
