package com.sep.testnc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class TestController {

	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("")
	public String test() {	
		ResponseEntity<String> response = restTemplate.getForEntity("https://localhost:8672/bank/api/test", String.class);
		return "  [from test NC]  " + response.getBody();
	}
	
	@RequestMapping("test")
	public String testTwo() {
		ResponseEntity<String> response = restTemplate.getForEntity("https://localhost:8673/bank/api/test", String.class);
		return "  [from test NC]  " + response.getBody();
	}
	
	@RequestMapping("testSeller")
	public String testThree() {
		ResponseEntity<String> response = restTemplate.getForEntity("https://localhost:8500/test", String.class);
		return "  [from test SELLERS]  " + response.getBody();
	}
	
}
