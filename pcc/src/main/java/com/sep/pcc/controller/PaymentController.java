package com.sep.pcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.sep.pcc.model.PCCRequest;
import com.sep.pcc.model.PCCResponse;
import com.sep.pcc.service.PaymentService;


@RestController
@RequestMapping("/")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;

	@PostMapping("/createPayment")
	public ResponseEntity<PCCResponse> createPayment(@RequestBody PCCRequest paymentRequest){
		return  new ResponseEntity<PCCResponse>(paymentService.createPayment(paymentRequest),HttpStatus.OK);
	}
}
