package com.sep.testnc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sep.testnc.dto.PaymentRequestDTO;

@RestController
@RequestMapping("/")
public class TestController {

	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping("/testPayment")
	public String testPayment(@RequestBody float price) {
		
		PaymentRequestDTO pr = new PaymentRequestDTO(1, price, "USD");
		ResponseEntity<String> response = restTemplate.postForEntity("https://localhost:8673/sellers/ncApi/paymentUrl", pr, String.class);
		
		return response.getBody();
	}
	
}
