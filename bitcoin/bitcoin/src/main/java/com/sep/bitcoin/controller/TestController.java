package com.sep.bitcoin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class TestController {

	@RequestMapping("/test")
	public String test() {
		return "Bitcoin test successful!";
	}
	
}