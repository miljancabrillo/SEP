package com.sep.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sep.bank.model.PaymentOrderStatus;
import com.sep.bank.model.PaymentRequest;
import com.sep.bank.service.BankService;
import com.sep.bank.utils.TokenUtils;

@RestController
@RequestMapping("/")
public class BankController {
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	BankService bankService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/createPayment")
	public ResponseEntity<String> createOrder() {
		
		PaymentRequest pr = tokenUtils.getPaymentRequest();
		String paymentUrl = bankService.createOrder(pr).getPaymentUrl();
		
		return new ResponseEntity<String>(paymentUrl, HttpStatus.OK);
	}
	
	@GetMapping("/success")
	public String success(@RequestParam long merchantOrderId) {
	    logger.info("Bank orderId="+ merchantOrderId +" successful");
		bankService.setPaymentOrderStatus(merchantOrderId, PaymentOrderStatus.PAID);
		return "https://localhost:8672/bank/success.html";
	}
	@GetMapping("/failure")
	public String failure(@RequestParam long merchantOrderId) {
	    logger.info("Bank orderId="+ merchantOrderId +" failed");
		bankService.setPaymentOrderStatus(merchantOrderId, PaymentOrderStatus.FAILED);
		return "https://localhost:8672/bank/failure.html";
	}
	
	@GetMapping("/error")
	public String error(@RequestParam long merchantOrderId) {
	    logger.info("Bank orderId="+ merchantOrderId +" failed");
		bankService.setPaymentOrderStatus(merchantOrderId, PaymentOrderStatus.FAILED);
		return "https://localhost:8672/bank/error.html";
	}

}
