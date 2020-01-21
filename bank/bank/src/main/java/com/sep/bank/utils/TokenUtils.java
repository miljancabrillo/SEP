package com.sep.bank.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sep.bank.model.PaymentRequest;
import com.sep.bank.security.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenUtils {
	
	@Autowired
	private JwtConfig jwtConfig;

	public PaymentRequest getPaymentRequest() {
		
		PaymentRequest paymentRequest = new PaymentRequest();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		paymentRequest.setSellerId(Long.parseLong(authentication.getPrincipal().toString()));
		
		String token = authentication.getDetails().toString();
		Map<String,Object> claims = getTokenClaims(token);
		
		paymentRequest.setPrice(Float.parseFloat(claims.get("price").toString()));
		paymentRequest.setCurrency(claims.get("currency").toString());
		
		return paymentRequest;
	}
	
	public long getSellerId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String token = authentication.getDetails().toString();
		Map<String,Object> claims = getTokenClaims(token);
		
		return Long.parseLong(claims.get("sellerId").toString());
	}
	
	private Map<String,Object> getTokenClaims(String token){
		
		Claims claims = Jwts.parser()
				.setSigningKey(jwtConfig.getSecret().getBytes())
				.parseClaimsJws(token)
				.getBody();
		
		return claims;
	}
	
}
