package com.sep.sellers.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sep.sellers.security.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class TestController {
	
	@Autowired
	JwtConfig jwtConfig;

	@PostMapping("/getJWT")
	public ResponseEntity<String> getJWT(){
		Long now = System.currentTimeMillis();
		String token = Jwts.builder()
			.setSubject("user")
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
			.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
			.compact();
		
		// Add token to header
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
}
