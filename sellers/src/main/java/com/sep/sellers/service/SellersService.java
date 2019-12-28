package com.sep.sellers.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sep.sellers.dto.PaymentRequestDTO;
import com.sep.sellers.dto.RegistrationRequestDTO;
import com.sep.sellers.model.Seller;
import com.sep.sellers.repository.SellersRepository;
import com.sep.sellers.security.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SellersService {
	
	@Autowired
	SellersRepository sellersRepository;
	
	@Autowired
	JwtConfig jwtConfig;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public RegistrationRequestDTO registration() {
		Seller seller  = new Seller();
		seller = sellersRepository.save(seller);
		logger.info("Registration link sellerId=" + seller.getId());
		RegistrationRequestDTO registrationRequest = new RegistrationRequestDTO(seller.getId(), "https://localhost:8672/sellers/registration.html?token=" + generateToken(seller.getId(), 0, ""));
		return registrationRequest;
		
	}

	public String generatePaymentUrl(PaymentRequestDTO pr) {
		//provjeriti da li postoji prodavac
		return "https://localhost:8672/sellers/paymentType.html?token=" + generateToken(pr.getSellerId(), pr.getPrice(), pr.getCurrency());
	}
	
	private String generateToken(long sellerId, float price, String currency) {
	
		Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("price", price);
		claimsMap.put("currency", currency);
		
		Long now = System.currentTimeMillis();
		String token = Jwts.builder()
			.setClaims(claimsMap)
			.setSubject(Long.toString(sellerId))
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
			.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
			.compact();
		return token;
	}
	
}
