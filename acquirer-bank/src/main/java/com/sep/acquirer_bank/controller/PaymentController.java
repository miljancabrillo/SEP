package com.sep.acquirer_bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sep.acquirer_bank.model.PaymentRequest;
import com.sep.acquirer_bank.model.PaymentResponse;
import com.sep.acquirer_bank.service.PaymentService;

@RestController
@RequestMapping("/")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;

	@PostMapping("/createPayment")
	public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest){
		return  new ResponseEntity<PaymentResponse>(paymentService.createPayment(paymentRequest),HttpStatus.OK);
	}
	
}
