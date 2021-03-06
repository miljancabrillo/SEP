package com.sep.paypal.controller;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sep.paypal.DTO.RegistrationDTO;
import com.sep.paypal.model.Seller;
import com.sep.paypal.repository.SellerRepository;
import com.sep.paypal.service.PayPalService;
import com.sep.paypal.utils.TokenUtils;

@Controller
public class RegistrationController {

	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	SellerRepository sellerRepository;
	
	@Autowired 
	PayPalService payPalService;
	
	@GetMapping("/getRegistrationForm")
	public String registrationForm() {
		return "registrationForm.html";
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationDTO regDTO){
		
		Seller seller = sellerRepository.findOneById(tokenUtils.getSellerId());
		if(seller != null) return new ResponseEntity<>("You are already registerd to paypal service!", HttpStatus.BAD_REQUEST);
		
		if(!StringUtils.isNotEmpty(regDTO.getClientId())) return new ResponseEntity<>("Filed client id is required!", HttpStatus.BAD_REQUEST);
		if(!StringUtils.isNotEmpty(regDTO.getClientSecret())) return new ResponseEntity<>("Filed client secret is required!", HttpStatus.BAD_REQUEST);
		if(!isValid(regDTO.getEmail())) return new ResponseEntity<>("Mail not valid!", HttpStatus.BAD_REQUEST);
		
		payPalService.register(regDTO);
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
