package com.sep.paypal.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.DTO.RegistrationDTO;
import com.sep.paypal.DTO.SubscriptionRequestDTO;
import com.sep.paypal.model.PaymentOrder;
import com.sep.paypal.model.PaymentOrderStatus;
import com.sep.paypal.model.PaymentRequest;
import com.sep.paypal.model.Seller;
import com.sep.paypal.repository.PaymentOrderRepository;
import com.sep.paypal.repository.SellerRepository;
import com.sep.paypal.utils.TokenUtils;

@Service
public class PayPalService {
	
	private static String SELLERS_URL = "https://localhost:8672/sellers/confirmPayment/";
	private static String KP_URL = "https://localhost:8672/paypal";

	@Value("${paypal.mode}")
	private String mode;
	
	@Autowired
	SellerRepository sellerRepository;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	PaymentOrderRepository paymentOrderRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Payment createPayment( PaymentRequest pr ) throws PayPalRESTException{
		
		Seller seller = sellerRepository.findOneById(pr.getSellerId());
		
		PaymentOrder po = new PaymentOrder();
		po.setSeller(seller);
		po.setPrice(pr.getPrice());
		po.setCurrency(pr.getCurrency());
		po.setSellersPaymentId(tokenUtils.getSellersPaymentId());
		paymentOrderRepository.save(po);
		
		Amount amount = new Amount();
		amount.setCurrency(pr.getCurrency());
		double total = new BigDecimal(pr.getPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

		Transaction transaction = new Transaction();
		transaction.setDescription("");
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("PAYPAL");
		
		Payment payment = new Payment();
		payment.setIntent("SALE");
		payment.setPayer(payer);  
		payment.setTransactions(transactions);
				
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(KP_URL+"/cancel.html?id=" + po.getId());
		redirectUrls.setReturnUrl(KP_URL+"/confirmPayment.html?id=" + po.getId());
		payment.setRedirectUrls(redirectUrls);
				
		payment = payment.create(getApiContext(seller.getPaypalClientId(), seller.getPaypalSecret()));
	    logger.info("Paypal order paypalId="+ payment.getId() +" created sellerId="+pr.getSellerId());
		
		po.setPaymentId(payment.getId());
		paymentOrderRepository.save(po);
		
		return payment;
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		
		PaymentOrder po = paymentOrderRepository.findOneByPaymentId(paymentId);
		Seller seller = po.getSeller();
		
		Payment payment = new Payment();
		
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		
		payment = payment.execute(getApiContext(seller.getPaypalClientId(), seller.getPaypalSecret()), paymentExecute);
		
		RestTemplate rt = new RestTemplate();
		
		if(payment.getState().equals("approved")) {
   		    logger.info("Paypal order paypalId="+ paymentId +" approved");
			po.setStatus(PaymentOrderStatus.PAID);
			rt.postForLocation(SELLERS_URL + po.getSellersPaymentId() + "/success", null);
		}else {
   		    logger.info("Paypal order paypalId="+ paymentId +" failed");
			po.setStatus(PaymentOrderStatus.FAILED);
			rt.postForLocation(SELLERS_URL + po.getSellersPaymentId() + "/failure", null);

		}
		paymentOrderRepository.save(po);
		
		return payment;
	}


	private APIContext getApiContext(String clientId, String clientSecret) throws PayPalRESTException {
		
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		
		APIContext context = new APIContext(new OAuthTokenCredential(clientId, clientSecret, configMap).getAccessToken());
		context.setConfigurationMap(configMap);
		return context;
	}
	
	public void canclePaymentOrder(String id) {
		PaymentOrder po = paymentOrderRepository.findOneById(id);
		po.setStatus(PaymentOrderStatus.CANCELED);
		logger.info("Paypal orderId="+ id +" canceled");
		paymentOrderRepository.save(po);
		RestTemplate rt = new RestTemplate();
		rt.postForLocation(SELLERS_URL + po.getSellersPaymentId() + "/failure", null);
	}

	public Double getPaymentOrderPrice(String paymentOrderId) {
		return paymentOrderRepository.findOneByPaymentId(paymentOrderId).getPrice();
	}
	
	 public void register(RegistrationDTO rDTO) {
			Seller seller = new Seller();
			seller.setId(tokenUtils.getSellerId());
			seller.setEmail(rDTO.getEmail());
			seller.setPaypalClientId(rDTO.getClientId());
			seller.setPaypalSecret(rDTO.getClientSecret());
			sellerRepository.save(seller);
	 }
		
}
