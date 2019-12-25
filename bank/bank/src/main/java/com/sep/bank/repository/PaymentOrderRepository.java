package com.sep.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.bank.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

	PaymentOrder findOneById(long id);
	
}
