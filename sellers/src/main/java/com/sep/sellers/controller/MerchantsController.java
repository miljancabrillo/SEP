package com.sep.sellers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sep.sellers.dto.RegistrationRequestDTO;
import com.sep.sellers.service.MerchantsService;

@RestController("/")
public class MerchantsController {

	@Autowired
	MerchantsService merchantsService;
	
	@PostMapping("/registration")
	public ResponseEntity<RegistrationRequestDTO> registration(){
		return new ResponseEntity<>(merchantsService.registration(), HttpStatus.OK);
	}
	
	
}
