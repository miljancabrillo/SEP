package com.sep.paypal.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payee;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.model.PaymentRequest;

@Service
public class PayPalService {
	
	private static String CLIENT_ID = "AaeQ69ERj4ff5giX9eVnXaRzsVWVjIFjmgnZUg2xYUej39kjiSs9HBg9jLyYOq1GTi-WGmgZBIYNIJwz";
	private static String CLIENT_SECRET = "ELM6TOIJh7t-zcMFwz50mzBUuk1nmm8z4dfFZh80-b7qOSORQ5eXM_DiY19zDX-VXsxKRivZE2dgFr8s";

	@Value("${paypal.mode}")
	private String mode;
	
	public Payment createPayment( PaymentRequest pr, String cancelUrl, String successUrl) throws PayPalRESTException{
		
		Amount amount = new Amount();
		amount.setCurrency("USD");
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
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

		return payment.create(getApiContext(CLIENT_ID, CLIENT_SECRET));
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(getApiContext(CLIENT_ID, CLIENT_SECRET), paymentExecute);
	}

	
	private APIContext getApiContext(String clientId, String clientSecret) throws PayPalRESTException {
		
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		
		APIContext context = new APIContext(new OAuthTokenCredential(clientId, clientSecret, configMap).getAccessToken());
		//APIContext context = new APIContext("A21AAG_H4i6oryT-qpwYF_j2bXFbbnrWdnZa7kLAvKOAc0Pl9Wm06elxR9VVhW_D4HfCcie0JdRLTvRO4puKEM3B2cGmVI1Bg");
		context.setConfigurationMap(configMap);
		return context;
	}
	
}
