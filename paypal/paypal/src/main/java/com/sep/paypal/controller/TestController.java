package com.sep.paypal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class TestController {

	@RequestMapping("/test")
	public String test() {
		return "Paypal test successful!";
	}
	
}
