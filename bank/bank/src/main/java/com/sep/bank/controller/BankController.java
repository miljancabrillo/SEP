package com.sep.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sep.bank.model.PaymentInitiationRequest;
import com.sep.bank.service.BankService;
import com.sep.bank.utils.TokenUtils;

@RestController
@RequestMapping("/")
public class BankController {
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	BankService bankService;
	
	@PostMapping("/createOrder")
	public ResponseEntity<String> createOrder() {
		
		PaymentInitiationRequest pr = tokenUtils.getPaymentRequest();
		String paymentUrl = bankService.createOrder(pr).getPaymentUrl();
		
		return new ResponseEntity<String>(paymentUrl, HttpStatus.OK);
	}
	
	@PostMapping("/executeOrder")
	public void execcuteOrder() {
		
	}

}
