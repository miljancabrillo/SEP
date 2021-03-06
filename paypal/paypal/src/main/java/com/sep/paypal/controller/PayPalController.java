package com.sep.paypal.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.inject.spi.Message;
import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.DTO.SubscriptionRequestDTO;
import com.sep.paypal.DTO.SubscriptionResponseDTO;
import com.sep.paypal.model.PaymentRequest;
import com.sep.paypal.model.Subscription;
import com.sep.paypal.repository.SellerRepository;
import com.sep.paypal.repository.SubscriptionRepository;
import com.sep.paypal.service.PayPalService;
import com.sep.paypal.service.SubscriptionService;
import com.sep.paypal.utils.TokenUtils;

@RequestMapping("/")
@RestController
public class PayPalController {

	@Autowired
	PayPalService payPalService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	SellerRepository sellerRepository;
	
	@Autowired
	SubscriptionService subscriptionService;
	
	@Autowired
	SubscriptionRepository subsRepository;
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/createPayment")
	public String createPayment() {
		
		PaymentRequest pr = tokenUtils.getPaymentRequest();
		try {
			Payment payment = payPalService.createPayment(pr);
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return link.getHref();
				}
			}			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return "https://localhost:8672/paypal/error.html";
	}
	
	@PostMapping("/ncApi/createSubscription")
	public SubscriptionResponseDTO createSuscription(@RequestBody SubscriptionRequestDTO subsDTO) {
		return subscriptionService.createSubscription(subsDTO);
	}
		
	@PostMapping("/cancelSubscription/{token}")
	public ResponseEntity<String> cancelSubscription(@PathVariable("token") String token) {
		String message = subscriptionService.cancelSubscription(token);
		if(message == "error") return new ResponseEntity<>("Subscription cancelation failed!", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("Subscription canceled successfuly!", HttpStatus.OK);
	}
	
	@PostMapping("/executeSubscription/{token}/{sellerId}")
	public String executeSubscription(@PathVariable("token") String token, @PathVariable("sellerId") long sellerId) {
		String status = subscriptionService.executeSubscription(token, sellerId);
		if(status.equals("error")) return "https://localhost:8672/paypal/errorSubscription.html";
		return "https://localhost:8672/paypal/successSubscription.html";
	}
	
	@PostMapping("/cancel/{id}")
	public void cancelPayment(@PathVariable("id") String id) {
		 logger.info("Paypal orderId="+ id +" canceled");
		 payPalService.canclePaymentOrder(id);
	}

	 @GetMapping("/success")
	 public String successfullPayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
	        try {
	            Payment payment = payPalService.executePayment(paymentId, payerId);
	            System.out.println(payment.toJSON());
	            if (payment.getState().equals("approved")) {
	                return "https://localhost:8672/paypal/success.html";
	            }
	        } catch (PayPalRESTException e) {
	         System.out.println(e.getMessage());
	        }
	        return "https://localhost:8672/paypal/error.html";
	    }

	 @PostMapping("/paymentOrderAmount")
	 public Double getPaymentOrderPrice(@RequestBody String paymentOrderId) {
		 return payPalService.getPaymentOrderPrice(paymentOrderId);
	 }
	
	 @PostMapping("/subscriptionDetails/{token}")
	 public SubscriptionRequestDTO getSubscriptionDetails(@PathVariable("token") String token) {
		 Subscription sub = subsRepository.findOneByAggrementToken(token);
		 System.out.println(subsRepository.findAll().toString());
		 SubscriptionRequestDTO subDTO = new SubscriptionRequestDTO();
		 subDTO.setName(sub.getName());
		 subDTO.setDescription(sub.getDescription());
		 
		 return subDTO;
	 }
	 
}
