package com.sep.pcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sep.pcc.model.PCCRequest;
import com.sep.pcc.model.PCCResponse;

@Service
public class PaymentService {

	private static String ISSUER_URL = "http://localhost:11200";
	
	@Autowired
	RestTemplate restTemplate;
	
	public PCCResponse createPayment(PCCRequest pccRequest) {
		
		ResponseEntity<PCCResponse> response = restTemplate.postForEntity(ISSUER_URL + "/executePayment", pccRequest, PCCResponse.class);
		
		return response.getBody();
	}
}
