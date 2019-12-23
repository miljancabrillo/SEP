package com.sep.bank.service;

import java.security.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sep.bank.model.PaymentRequest;
import com.sep.bank.model.PaymentResponse;
import com.sep.bank.model.Seller;
import com.sep.bank.repository.PaymentRepository;
import com.sep.bank.repository.SellersRepository;
import com.sep.bank.model.Payment;
import com.sep.bank.model.PaymentInitiationRequest;

@Service
public class BankService {
	
	private static String BANK_URL = "https://localhost:11000";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	SellersRepository sellerRepository;
	
	@Autowired
	PaymentRepository paymentRepository;

	public PaymentResponse createOrder(PaymentInitiationRequest pir) {
		
		//Seller seller = sellerRepository.findOneById(pir.getSellerId());
		
		Seller seller = new Seller(1, "merchantOne", "merchantPass");
		
		Payment payment = new Payment();
		payment.setSeller(seller);
		payment.setAmount(pir.getPrice());
		payment.setTimestamp(new Date());
		payment = paymentRepository.save(payment);
		
		PaymentRequest por = new PaymentRequest();
		
		por.setMerchantId(seller.getMerchantId());
		por.setMerchantPassword(seller.getMerchantPassword());
		por.setAmount(pir.getPrice());
		por.setMerchantOrderId(payment.getId());
		por.setMerchantTimestamp(payment.getTimestamp());
		por.setSuccesUrl("");
		por.setFailedUrl("");
		por.setErrorUrl("");
		
		ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(BANK_URL + "/createPayment", por, PaymentResponse.class);
		
		return response.getBody();
	}

	
	
}

