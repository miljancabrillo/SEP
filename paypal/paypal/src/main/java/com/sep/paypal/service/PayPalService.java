package com.sep.paypal.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.model.PaymentOrder;
import com.sep.paypal.model.PaymentOrderStatus;
import com.sep.paypal.model.PaymentRequest;
import com.sep.paypal.model.Seller;
import com.sep.paypal.repository.PaymentOrderRepository;
import com.sep.paypal.repository.SellerRepository;

@Service
public class PayPalService {
	
	private static String KP_URL = "https://localhost:8672/paypal";

	@Value("${paypal.mode}")
	private String mode;
	
	@Autowired
	SellerRepository sellerRepository;
	
	@Autowired
	PaymentOrderRepository paymentOrderRepository;
	
	public Payment createPayment( PaymentRequest pr ) throws PayPalRESTException{
		
		Seller seller = sellerRepository.findOneById(pr.getSellerId());
		
		PaymentOrder po = new PaymentOrder();
		po.setSeller(seller);
		po.setPrice(pr.getPrice());
		po.setCurrency(pr.getCurrency());
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
		redirectUrls.setCancelUrl(KP_URL+"/cancel.html?id=" + Long.toString(po.getId()));
		redirectUrls.setReturnUrl(KP_URL+"/confirmPayment.html");
		payment.setRedirectUrls(redirectUrls);
		
		
				
		payment = payment.create(getApiContext(seller.getPaypalClientId(), seller.getPaypalSecret()));
		
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
		
		if(payment.getState().equals("approved")) {
			po.setStatus(PaymentOrderStatus.PAID);
		}else {
			po.setStatus(PaymentOrderStatus.FAILED);
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
	
	public void canclePaymentOrder(long id) {
		PaymentOrder po = paymentOrderRepository.findOneById(id);
		po.setStatus(PaymentOrderStatus.CANCELED);
		paymentOrderRepository.save(po);
	}

	public Double getPaymentOrderPrice(String paymentOrderId) {
		return paymentOrderRepository.findOneByPaymentId(paymentOrderId).getPrice();
	}
}
