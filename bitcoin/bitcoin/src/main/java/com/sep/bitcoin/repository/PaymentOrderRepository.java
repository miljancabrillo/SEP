package com.sep.bitcoin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.bitcoin.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, String> {

	@SuppressWarnings("unchecked")
	public PaymentOrder save(PaymentOrder paymentOrder);
	
	public PaymentOrder findOneByOrderId(String orderId);
	
}
