package com.sep.paypal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sep.paypal.model.PaymentRequest;
import com.sep.paypal.utils.TokenUtils;

@RequestMapping("/")
@RestController
public class TestController {

	@Autowired
	TokenUtils tokenUtils;
	
	@RequestMapping("/test")
	public String test() {
		return "Paypal test successful!";
	}
	
	@RequestMapping("/testToken")
	public String testToken() {
		PaymentRequest pr = tokenUtils.getPaymentRequest();
		return pr.toString();
	}
	
}
