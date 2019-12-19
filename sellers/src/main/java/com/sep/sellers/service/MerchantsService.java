package com.sep.sellers.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sep.sellers.dto.RegistrationRequestDTO;
import com.sep.sellers.model.Merchant;
import com.sep.sellers.repository.MerchantsRepository;
import com.sep.sellers.security.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class MerchantsService {
	
	@Autowired
	MerchantsRepository merchantsRepository;
	
	@Autowired
	JwtConfig jwtConfig;
	
	public RegistrationRequestDTO registration() {
		Merchant merchant  = new Merchant();
		merchant = merchantsRepository.save(merchant);
		RegistrationRequestDTO registrationRequest = new RegistrationRequestDTO(merchant.getId(), generateRegistrationUrl(merchant));
		return registrationRequest;
		
	}

	private String generateRegistrationUrl(Merchant merchant) {
		return "https://localhost:8762/sellers/registration.html?token=" + generateToken(merchant);
	}
	
	private String generateToken(Merchant merchant) {
		Long now = System.currentTimeMillis();
		String token = Jwts.builder()
			.setSubject(Long.toString(merchant.getId()))
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
			.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
			.compact();
		return token;
	}
	
}
