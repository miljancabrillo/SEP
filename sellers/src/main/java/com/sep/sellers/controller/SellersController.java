package com.sep.sellers.controller;

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
	
	@PostMapping("/nc/registrationUrl")
	public ResponseEntity<RegistrationRequestDTO> registration(){
		return new ResponseEntity<>(sellersService.registration(), HttpStatus.OK);
	}
	
	@PostMapping("/nc/paymentUrl")
	public String payment(@RequestBody PaymentRequestDTO pr) {
		return sellersService.generatePaymentUrl(pr);
	}
	
}
