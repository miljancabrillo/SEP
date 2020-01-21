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
import com.sep.sellers.dto.RegistrationResponseDTO;
import com.sep.sellers.model.PaymentType;
import com.sep.sellers.model.RegistrationData;
import com.sep.sellers.model.RegistrationStatus;
import com.sep.sellers.model.Seller;
import com.sep.sellers.repository.PaymentTypeRepository;
import com.sep.sellers.repository.RegistrationDataRepository;
import com.sep.sellers.repository.SellersRepository;
import com.sep.sellers.security.JwtConfig;
import com.sep.sellers.utils.TokenUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SellersService {
	
	@Autowired
	SellersRepository sellersRepository;
	
	@Autowired
	JwtConfig jwtConfig;
	
	@Autowired
	PaymentTypeRepository ptRepository;
	
	@Autowired
	RegistrationDataRepository registrationDataRepository;
	
	@Autowired
	TokenUtils tokenUtils;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public RegistrationResponseDTO generateRegistrationUrl(RegistrationRequestDTO requestDTO) {
		Seller seller  = new Seller();
		seller = sellersRepository.save(seller);
		
		RegistrationData regData = new RegistrationData();
		regData.setConfirmationUrl(requestDTO.getConfirmationLink());
		regData.setSeller(seller);
		regData = registrationDataRepository.save(regData);
		
		
		logger.info("Registration link sellerId=" + seller.getId());
		String registrationUrl = "https://localhost:8672/sellers/registration.html?token="+generateToken(seller.getId(), 0, "", regData.getId());
		RegistrationResponseDTO responseDTO = new RegistrationResponseDTO(seller.getId(), registrationUrl);
		return responseDTO;
		
	}
	
	public String confirmRegistration(long paymentTypeId) {
		
		PaymentType paymentType = ptRepository.findOneById(paymentTypeId);
		if(paymentType == null) return "error";
		
		Seller seller = sellersRepository.findOneById(tokenUtils.getSellerId());
		seller.addPaymentType(paymentType);
		sellersRepository.save(seller);
		
		RegistrationData rd = registrationDataRepository.findOneById(tokenUtils.getRegistrationDataId());
		if(rd.getStatus() == RegistrationStatus.UNCONFIRMED) {
			//aktiviram confirmation link
			rd.setStatus(RegistrationStatus.CONFIRMED);
			registrationDataRepository.save(rd);
		}
		
		return "success";
	}

	public String generatePaymentUrl(PaymentRequestDTO pr) {
		//provjeriti da li postoji prodavac
		return "https://localhost:8672/sellers/paymentType.html?token=" + generateToken(pr.getSellerId(), pr.getPrice(), pr.getCurrency(),-1);
	}
	
	private String generateToken(long sellerId, float price, String currency, long regDataId) {
	
		Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("sellerId", sellerId);
		claimsMap.put("price", price);
		claimsMap.put("currency", currency);
		if(regDataId != -1) claimsMap.put("regDataId", regDataId);
		
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
