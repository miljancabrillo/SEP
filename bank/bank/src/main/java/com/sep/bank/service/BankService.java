package com.sep.bank.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sep.bank.model.AcquirerRequest;
import com.sep.bank.model.AcquirerResponse;
import com.sep.bank.model.Seller;
import com.sep.bank.repository.PaymentOrderRepository;
import com.sep.bank.repository.SellersRepository;
import com.sep.bank.model.PaymentOrder;
import com.sep.bank.model.PaymentOrderStatus;
import com.sep.bank.model.PaymentRequest;

@Service
public class BankService {
	
	private static String BANK_URL = "https://localhost:11000";
	private static String KP_URL = "https://localhost:8672/bank";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	SellersRepository sellerRepository;
	
	@Autowired
	PaymentOrderRepository paymentOrderRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public AcquirerResponse createOrder(PaymentRequest pr) {
		
		Seller seller = sellerRepository.findOneById(pr.getSellerId());
		
		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setSeller(seller);
		paymentOrder.setAmount(pr.getPrice());
		paymentOrder.setTimestamp(new Date());
		paymentOrder = paymentOrderRepository.save(paymentOrder);
		
		AcquirerRequest ar = new AcquirerRequest();
		
		ar.setMerchantId(seller.getMerchantId());
		ar.setMerchantPassword(seller.getMerchantPassword());
		ar.setAmount(pr.getPrice());
		ar.setMerchantOrderId(paymentOrder.getId());
		ar.setMerchantTimestamp(paymentOrder.getTimestamp());
		ar.setSuccesUrl(KP_URL + "/success");
		ar.setFailedUrl(KP_URL + "/failure");
		ar.setErrorUrl(KP_URL + "/error");
		
		ResponseEntity<AcquirerResponse> response = restTemplate.postForEntity(BANK_URL + "/createPayment", ar, AcquirerResponse.class);
		
	    logger.info("Bank orderId="+ paymentOrder.getId() +" created sellerId=" + pr.getSellerId());

		
		paymentOrder.setPaymentId(response.getBody().getPaymentId());
		paymentOrderRepository.save(paymentOrder);
		
		return response.getBody();
	}

	public void setPaymentOrderStatus(long id, PaymentOrderStatus status) {
		PaymentOrder po = paymentOrderRepository.findOneById(id);
		po.setStatus(status);
		paymentOrderRepository.save(po);
	}
	
}

