package com.sep.bank.controller;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sep.bank.DTO.RegistrationDTO;
import com.sep.bank.model.Seller;
import com.sep.bank.repository.SellersRepository;
import com.sep.bank.service.BankService;
import com.sep.bank.utils.TokenUtils;


@Controller
public class RegistrationController {

	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	SellersRepository sellerRepository;
	
	@Autowired 
	BankService bankService;
	
	@GetMapping("/getRegistrationForm")
	public String registrationForm() {
		return "registrationForm.html";
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationDTO regDTO){
		
		Seller seller = sellerRepository.findOneById(tokenUtils.getSellerId());
		if(seller != null) return new ResponseEntity<>("You are already registerd to paypal service!", HttpStatus.BAD_REQUEST);
		
		if(!StringUtils.isNotEmpty(regDTO.getClientId())) return new ResponseEntity<>("Filed client id is required!", HttpStatus.BAD_REQUEST);
		try {
			Long.parseLong(regDTO.getClientId());
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return new ResponseEntity<>("Filed client must be number!", HttpStatus.BAD_REQUEST);
		}
		if(!StringUtils.isNotEmpty(regDTO.getClientPassword())) return new ResponseEntity<>("Filed client password is required!", HttpStatus.BAD_REQUEST);
		if(!isValid(regDTO.getEmail())) return new ResponseEntity<>("Mail not valid!", HttpStatus.BAD_REQUEST);
		
		bankService.register(regDTO);
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
