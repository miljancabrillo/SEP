package com.sep.sellers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sep.sellers.dto.PaymentRequestDTO;
import com.sep.sellers.dto.RegistrationRequestDTO;
import com.sep.sellers.service.SellersService;

@RestController("/")
public class SellersController {

	@Autowired
	SellersService sellersService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/ncApi/registrationUrl")
	public ResponseEntity<RegistrationRequestDTO> registration(){
		return new ResponseEntity<>(sellersService.registration(), HttpStatus.OK);
	}
	
	@PostMapping("/ncApi/paymentUrl")
	public String payment(@RequestBody PaymentRequestDTO pr) {
		logger.info("Payment URL request sellerId=" + pr.getSellerId());
		return sellersService.generatePaymentUrl(pr);
	}
	
}
