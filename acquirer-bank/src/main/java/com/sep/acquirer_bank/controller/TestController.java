package com.sep.acquirer_bank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

	@RequestMapping("/test")
	public String test() {
		return "  [from test bank]  ";
	}
}
