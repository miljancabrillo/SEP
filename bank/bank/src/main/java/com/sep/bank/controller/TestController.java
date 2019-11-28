package com.sep.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/")
@RestController
public class TestController {

	@RequestMapping("/test")
	public String test() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:11000/test", String.class);
		return "Bank test successful!" + "\nRecived data:" + response.getBody();
	}
	
}
