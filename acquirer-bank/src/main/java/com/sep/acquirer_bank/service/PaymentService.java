package com.sep.acquirer_bank.service;

import org.springframework.stereotype.Service;

import com.sep.acquirer_bank.model.PaymentRequest;
import com.sep.acquirer_bank.model.PaymentResponse;

@Service
public class PaymentService {

	public PaymentResponse createPayment(PaymentRequest paymentRequest) {
		return new PaymentResponse(1,"https://localhost:11000/payment.html");
	}
	
}
