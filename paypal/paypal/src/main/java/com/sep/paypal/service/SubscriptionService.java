package com.sep.paypal.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.ChargeModels;
import com.paypal.api.payments.Currency;
import com.paypal.api.payments.MerchantPreferences;
import com.paypal.api.payments.Patch;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PaymentDefinition;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.sep.paypal.DTO.SubscriptionDTO;
import com.sep.paypal.model.Seller;
import com.sep.paypal.repository.SellerRepository;
import com.sep.paypal.utils.TokenUtils;

@Service
public class SubscriptionService {
	
	@Autowired
	SellerRepository sellerRepository;
	
	@Autowired
	TokenUtils tokenUtils;

	private static String KP_URL = "https://localhost:8672/paypal";
	
	public Agreement createSubscription(SubscriptionDTO subsDTO) throws PayPalRESTException, MalformedURLException, UnsupportedEncodingException{
		
		Seller seller = sellerRepository.getOne(subsDTO.getSellerId());
		
		Plan plan = null;
		
		try {
			plan = createBillingPlan(subsDTO, seller);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Agreement agreement = new Agreement();
		
		agreement.setName(subsDTO.getName());
		agreement.setDescription(subsDTO.getDescription());
		
		
		Date date = new Date(System.currentTimeMillis() + 86400000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		
		agreement.setStartDate(sdf.format(date));
		
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		agreement.setPayer(payer);
		
		Plan newPlan = new Plan();
		newPlan.setId(plan.getId());
		agreement.setPlan(newPlan);
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(KP_URL+"/cancelSubscription.html?id=");
		redirectUrls.setReturnUrl(KP_URL+"/confirmSubscription.html");
		
		MerchantPreferences merchantPreferences = new MerchantPreferences();
		merchantPreferences.setCancelUrl(KP_URL+"/cancelSubscription.html?id=");
		merchantPreferences.setReturnUrl(KP_URL+"/confirmSubscription.html");
		agreement.setOverrideMerchantPreferences(merchantPreferences);
		
		agreement = agreement.create(getApiContext(seller.getPaypalClientId(), seller.getPaypalSecret()));
		
		return agreement;
	}
	
	public Plan createBillingPlan(SubscriptionDTO subsDTO, Seller seller) throws PayPalRESTException {
		// Build Plan object
		Plan plan = new Plan();
		plan.setName(subsDTO.getName());
		plan.setDescription(subsDTO.getDescription());
		plan.setType(subsDTO.getType());
		
		//payment_definitions
		PaymentDefinition paymentDefinition = new PaymentDefinition();
		paymentDefinition.setName("Regular Payments");
		paymentDefinition.setType("REGULAR");
		paymentDefinition.setFrequency(subsDTO.getFrequency());
		paymentDefinition.setFrequencyInterval(subsDTO.getFrequencyIntrval());
		paymentDefinition.setCycles(subsDTO.getCycles());

		//currency
		Currency currency = new Currency();
		currency.setCurrency(subsDTO.getCurrency());
		currency.setValue(subsDTO.getAmount());
		paymentDefinition.setAmount(currency);

		//charge_models
		ChargeModels chargeModels = new com.paypal.api.payments.ChargeModels();
		chargeModels.setType("SHIPPING");
		chargeModels.setAmount(currency);
		List<ChargeModels> chargeModelsList = new ArrayList<ChargeModels>();
		chargeModelsList.add(chargeModels);
		paymentDefinition.setChargeModels(chargeModelsList);
		

		//payment_definition
		List<PaymentDefinition> paymentDefinitionList = new ArrayList<PaymentDefinition>();
		paymentDefinitionList.add(paymentDefinition);
		plan.setPaymentDefinitions(paymentDefinitionList);

		//merchant_preferences
		MerchantPreferences merchantPreferences = new MerchantPreferences();
		//merchantPreferences.setSetupFee(currency);
		merchantPreferences.setCancelUrl("http://www.cancel.com");
		merchantPreferences.setReturnUrl("http://www.return.com");
		//merchantPreferences.setMaxFailAttempts("0");
		//merchantPreferences.setAutoBillAmount("YES");
		//merchantPreferences.setInitialFailAmountAction("CONTINUE");*/
		plan.setMerchantPreferences(merchantPreferences);

		APIContext context = getApiContext(seller.getPaypalClientId(), seller.getPaypalSecret());
		plan = plan.create(context);
		
		
		List<Patch> patchRequestList = new ArrayList<Patch>();
		Map<String, String> value = new HashMap<String, String>();
		value.put("state", "ACTIVE");

		Patch patch = new Patch();
		patch.setPath("/");
		patch.setValue(value);
		patch.setOp("replace");
		patchRequestList.add(patch);

		plan.update(context, patchRequestList);
		return plan;
	}
	
	private APIContext getApiContext(String clientId, String clientSecret) throws PayPalRESTException {
		
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", "sandbox");
		
		APIContext context = new APIContext(new OAuthTokenCredential(clientId, clientSecret, configMap).getAccessToken());
		context.setConfigurationMap(configMap);
		return context;
	}
	
	
}
