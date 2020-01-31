package com.sep.bank.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sep.bank.DTO.RegistrationDTO;
import com.sep.bank.model.AcquirerRequest;
import com.sep.bank.model.AcquirerResponse;
import com.sep.bank.model.Seller;
import com.sep.bank.repository.PaymentOrderRepository;
import com.sep.bank.repository.SellersRepository;
import com.sep.bank.utils.TokenUtils;
import com.sep.bank.model.PaymentOrder;
import com.sep.bank.model.PaymentOrderStatus;
import com.sep.bank.model.PaymentRequest;

@Service
@EnableScheduling
public class BankService {
	
	private static String SELLERS_URL = "https://localhost:8672/sellers/confirmPayment/";
	private static String BANK_URL = "https://localhost:11000";
	private static String KP_URL = "https://localhost:8672/bank";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	SellersRepository sellerRepository;
	
	@Autowired
	PaymentOrderRepository paymentOrderRepository;
	
	@Autowired
	TokenUtils tokenUtils;
	
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
		paymentOrder.setSellersPaymentId(tokenUtils.getSellersPaymentId());
		paymentOrderRepository.save(paymentOrder);
		
		return response.getBody();
	}

	public void setPaymentOrderStatus(long id, PaymentOrderStatus status) {
		PaymentOrder po = paymentOrderRepository.findOneById(id);
		po.setStatus(status);
		paymentOrderRepository.save(po);
		RestTemplate rt = new RestTemplate();
		if(status == PaymentOrderStatus.PAID) {
			rt.postForLocation(SELLERS_URL + po.getSellersPaymentId() + "/success", null);
		}else if(status == PaymentOrderStatus.FAILED) {
			rt.postForLocation(SELLERS_URL + po.getSellersPaymentId() + "/failure", null);
		}
	}
	

	 public void register(RegistrationDTO rDTO) {
			Seller seller = new Seller();
			seller.setId(tokenUtils.getSellerId());
			seller.setEmail(rDTO.getEmail());
			seller.setMerchantId(rDTO.getClientId());
			seller.setMerchantPassword(rDTO.getClientPassword());
			sellerRepository.save(seller); 
	}
	 
	@Scheduled(fixedRate = 6000)
	public void checkStatus() {
		List<PaymentOrder> orders = paymentOrderRepository.findAll();
		if(orders == null) return;
		for (PaymentOrder paymentOrder : orders) {
			if(paymentOrder.getStatus() != PaymentOrderStatus.CREATED) continue;
			ResponseEntity<String> response = restTemplate.getForEntity(BANK_URL + "/checkStatus/" + Long.toString(paymentOrder.getPaymentId()), String.class);
			System.out.println(response.getBody());
			RestTemplate rt = new RestTemplate();

			if(response.getBody().equals("failed")) {
				paymentOrder.setStatus(PaymentOrderStatus.FAILED);
				rt.postForLocation(SELLERS_URL + paymentOrder.getSellersPaymentId() + "/failure", null);
			}
			if(response.getBody().equals("successful")) {
				paymentOrder.setStatus(PaymentOrderStatus.PAID);
				rt.postForLocation(SELLERS_URL + paymentOrder.getSellersPaymentId() + "/success", null);
			}
			paymentOrderRepository.save(paymentOrder);
		}
	}
}

