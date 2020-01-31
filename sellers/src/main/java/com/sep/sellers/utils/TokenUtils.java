package com.sep.sellers.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sep.sellers.security.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenUtils {
	
	@Autowired
	private JwtConfig jwtConfig;
	
	public long getRegistrationDataId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String token = authentication.getDetails().toString();
		Map<String,Object> claims = getTokenClaims(token);
		
		return Long.parseLong(claims.get("regDataId").toString());
	}
	
	public long getSellerId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return Long.parseLong(authentication.getPrincipal().toString());
	}
	
	public String getSellersPaymentId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String token = authentication.getDetails().toString();
		Map<String,Object> claims = getTokenClaims(token);
		
		return claims.get("sellersPaymentId").toString();
	}
	
	private Map<String,Object> getTokenClaims(String token){
		
		Claims claims = Jwts.parser()
				.setSigningKey(jwtConfig.getSecret().getBytes())
				.parseClaimsJws(token)
				.getBody();
		
		return claims;
	}
	
}
