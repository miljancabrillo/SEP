package com.sep.acquirer_bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sep.acquirer_bank.model.ExecutePayment;
import com.sep.acquirer_bank.model.AcquirerRequest;
import com.sep.acquirer_bank.model.AcquirerResponse;
import com.sep.acquirer_bank.service.PaymentService;

@RestController
@RequestMapping("/")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;

	@PostMapping("/createPayment")
	public ResponseEntity<AcquirerResponse> createPayment(@RequestBody AcquirerRequest paymentRequest){
		return  new ResponseEntity<AcquirerResponse>(paymentService.createPayment(paymentRequest),HttpStatus.OK);
	}
	
	@PostMapping("/executePayment")
	public String executePayment(@RequestBody ExecutePayment executePayment) {
		return paymentService.executePayment(executePayment);
	}
	
}
