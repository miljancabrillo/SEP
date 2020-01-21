package com.sep.sellers.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sep.sellers.dto.PaymentRequestDTO;
import com.sep.sellers.dto.RegistrationRequestDTO;
import com.sep.sellers.dto.RegistrationResponseDTO;
import com.sep.sellers.model.PaymentType;
import com.sep.sellers.repository.PaymentTypeRepository;
import com.sep.sellers.service.SellersService;


@RestController("/")
public class SellersController {

	@Autowired
	SellersService sellersService;
	
	@Autowired
	PaymentTypeRepository ptRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/ncApi/registrationUrl")
	public ResponseEntity<RegistrationResponseDTO> registration(RegistrationRequestDTO requestDTO){
		return new ResponseEntity<>(sellersService.generateRegistrationUrl(requestDTO), HttpStatus.OK);
	}
	
	@PostMapping("/ncApi/paymentUrl")
	public String payment(@RequestBody PaymentRequestDTO pr) {
		logger.info("Payment URL request sellerId=" + pr.getSellerId());
		return sellersService.generatePaymentUrl(pr);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/confirmRegistration/{paymentTypeId}")
	public ResponseEntity confirmRegistration(@PathVariable("paymentTypeId") long paymentTypeId) {
		if(sellersService.confirmRegistration(paymentTypeId) == "success") return new ResponseEntity<>(HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/paymentTypes")
	public ResponseEntity<List<PaymentType>> getPaymentTypes(){
		return new ResponseEntity<>(ptRepository.findAll(), HttpStatus.OK);
	}
}
