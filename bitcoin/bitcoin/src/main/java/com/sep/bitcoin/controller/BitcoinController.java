package com.sep.bitcoin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sep.bitcoin.model.PaymentRequest;
import com.sep.bitcoin.service.BitcoinService;
import com.sep.bitcoin.utils.TokenUtils;

@RequestMapping("/")
@RestController
public class BitcoinController {

	@Autowired
	BitcoinService bitcoinService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@PostMapping("/createPayment")
	public String createPayment() {
		PaymentRequest pr = tokenUtils.getPaymentRequest();
		return bitcoinService.createPayment(pr);
	}
	
	@PostMapping("/cancel")
	public void cancelPaymentOrder(@RequestBody String paymentOrderId) {
		bitcoinService.setPaymentOrderStatus(paymentOrderId, "canceled");
	}
	
	@PostMapping("/success")
	public void succesPaymentOrder(@RequestBody String paymentOrderId) {
		bitcoinService.setPaymentOrderStatus(paymentOrderId, "paid");
	}
}
