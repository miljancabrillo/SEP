package com.sep.bitcoin.controller;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sep.bitcoin.DTO.RegistrationDTO;
import com.sep.bitcoin.model.Seller;
import com.sep.bitcoin.repository.SellerRepository;
import com.sep.bitcoin.service.BitcoinService;
import com.sep.bitcoin.utils.TokenUtils;

@Controller
public class RegistrationController {

	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	SellerRepository sellerRepository;
	
	@Autowired
	BitcoinService bitcoinService;
	
	@GetMapping("/getRegistrationForm")
	public String registrationForm() {
		return "registrationForm.html";
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationDTO regDTO){
		
		Seller seller = sellerRepository.findOneById(tokenUtils.getSellerId());
		if(seller != null) return new ResponseEntity<>("You are already registerd to bitcoin service!", HttpStatus.BAD_REQUEST);
		
		if(!StringUtils.isNotEmpty(regDTO.getAccessToken())) return new ResponseEntity<>("Filed acces token is required!", HttpStatus.BAD_REQUEST);
		if(!isValid(regDTO.getEmail())) return new ResponseEntity<>("Mail not valid!", HttpStatus.BAD_REQUEST);
		
		bitcoinService.register(regDTO);
		return new ResponseEntity<>("Success!", HttpStatus.OK);
	}
	
	 public static boolean isValid(String email) { 
	    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                        "[a-zA-Z0-9_+&*-]+)*@" + 
	                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                        "A-Z]{2,7}$"; 
	                          
	    Pattern pat = Pattern.compile(emailRegex); 
	    if (email == null) 
	    	return false; 
	        return pat.matcher(email).matches(); 
	 } 
}
