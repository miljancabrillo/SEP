package com.sep.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.paypal.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
	
	PaymentOrder findOneById(String id);
	
	@SuppressWarnings("unchecked")
	PaymentOrder save(PaymentOrder po);
	
	PaymentOrder findOneByPaymentId(String paymentId);

}
