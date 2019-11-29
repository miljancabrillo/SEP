package com.sep.testnc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class TestController {

	
	@RequestMapping("")
	public String test() {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("https://localhost:8672/bank/test", String.class);
		
		return "  [from test NC]  " + response.getBody();
	}
	
}
