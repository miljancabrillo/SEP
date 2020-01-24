package com.sep.issuer_bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sep.issuer_bank.model.PCCRequest;
import com.sep.issuer_bank.model.PCCResponse;
import com.sep.issuer_bank.service.PaymentService;


@RestController
@RequestMapping("/")
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	
	@PostMapping("/executePayment")
	public ResponseEntity<PCCResponse> executePayment(@RequestBody PCCRequest paymentRequest){
		return  new ResponseEntity<PCCResponse>(paymentService.executePayment(paymentRequest),HttpStatus.OK);
	}
}
