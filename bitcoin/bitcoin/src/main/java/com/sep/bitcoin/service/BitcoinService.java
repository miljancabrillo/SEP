package com.sep.bitcoin.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sep.bitcoin.model.PaymentOrder;
import com.sep.bitcoin.model.PaymentRequest;
import com.sep.bitcoin.model.Seller;
import com.sep.bitcoin.repository.PaymentOrderRepository;
import com.sep.bitcoin.repository.SellerRepository;


@Service
@EnableScheduling
public class BitcoinService {

	private static String KP_URL = "https://localhost:8672/bitcoin";
	private static String COINGATE_URL = "https://api-sandbox.coingate.com/v2/orders/";

	
	@Autowired
	SellerRepository sellerRepository;
	
	@Autowired
	PaymentOrderRepository paymentOrderRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String createPayment(PaymentRequest pr) {
	
		PaymentOrder po = new PaymentOrder();
		Seller payee = sellerRepository.findOneById(pr.getSellerId());
		
		po.setPriceAmount(pr.getPrice());
		po.setPriceCurrency(pr.getCurrency());
		po.setReceiveCurrency(pr.getCurrency());
		po = paymentOrderRepository.save(po);

		po.setCancelUrl(KP_URL + "/cancel.html?id=" + po.getOrderId());
		po.setSuccesUrl(KP_URL + "/success.html?id=" + po.getOrderId());
		
		
		String authorizationHeader = "Bearer " + payee.getAccesToken();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", authorizationHeader);
		
		HttpEntity<PaymentOrder> requestEntity = new HttpEntity<PaymentOrder>(po, requestHeaders);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<PaymentOrder> responseEntity = rt.exchange(COINGATE_URL, HttpMethod.POST, requestEntity,
				PaymentOrder.class);

		PaymentOrder responsePo = null;
		
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			
			responsePo = responseEntity.getBody();
			po.setCreatedAt(responsePo.getCreatedAt());
			po.setStatus(responsePo.getStatus());
			po.setPayee(payee);
			po.setId(responsePo.getId());
			paymentOrderRepository.save(po);
			if(responsePo.getStatus().equals("new")) {
			    logger.info("Bitcoin orderId="+po.getOrderId() +" sellerId="+ pr.getSellerId() +" created");
				return responsePo.getPaymentUrl();
			}else {
			    logger.info("Bitcoin orderId="+po.getOrderId() +" sellerId="+ pr.getSellerId() +" cration failed");
				return KP_URL+"error.html";
			}
		}

	     return KP_URL+"error.hrml";
			
	}

	@Scheduled(fixedRate = 60000)
	public void checkPaymentStatus() {
		
		List<PaymentOrder> poList = paymentOrderRepository.findAll();
		
		if(poList == null) return;
		
		for(PaymentOrder po : poList) {
		
			if(po.getStatus().equals("new") || po.getStatus().equals("pending") || po.getStatus().equals("confirming")) {
				
				Seller payee = po.getPayee();
				
				String authorizationHeader = "Bearer " + payee.getAccesToken();

				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setContentType(MediaType.APPLICATION_JSON);
				requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				requestHeaders.add("Authorization", authorizationHeader);
				HttpEntity<String> entity = new HttpEntity<>(null, requestHeaders);

				RestTemplate rt = new RestTemplate();
				ResponseEntity<PaymentOrder> response = rt.exchange(COINGATE_URL + Long.toString(po.getId()), HttpMethod.GET, entity, PaymentOrder.class);
				
			    logger.info("Bitcoin orderId=" + po.getOrderId() +" status=" + response.getBody().getStatus());
				po.setStatus(response.getBody().getStatus());
				
				//ovjde bi trebalo obavjestiti nc ako je placanje uspjeno
				
				paymentOrderRepository.save(po);
				
			}		
		}
	} 
	
	public void setPaymentOrderStatus(String poId, String status) {
		PaymentOrder po = paymentOrderRepository.findOneByOrderId(poId);
		po.setStatus(status);
		paymentOrderRepository.save(po);
	}
}
