package com.sep.acquirer_bank.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sep.acquirer_bank.model.ExecutePayment;
import com.sep.acquirer_bank.model.PCCRequest;
import com.sep.acquirer_bank.model.PCCResponse;
import com.sep.acquirer_bank.model.AcquirerRequest;
import com.sep.acquirer_bank.model.AcquirerResponse;
import com.sep.acquirer_bank.model.Payment;
import com.sep.acquirer_bank.model.PaymentStatus;
import com.sep.acquirer_bank.model.UserAccount;
import com.sep.acquirer_bank.repository.PCCRequestRepository;
import com.sep.acquirer_bank.repository.PaymentRepository;
import com.sep.acquirer_bank.repository.UserAccountRepository;

@Service
public class PaymentService {

	private static String PCC_URL = "http://localhost:11100";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	PCCRequestRepository pccRepository;
	
	public AcquirerResponse createPayment(AcquirerRequest paymentRequest) {
		//treba uraditi sve provjere da li je validan merchatId i merchantPassword
		
		UserAccount payee = userAccountRepository.findOneById(Long.parseLong(paymentRequest.getMerchantId()));
		
		Payment pt = new Payment();
		pt.setPayee(payee);
		pt.setMerchantOrderId(paymentRequest.getMerchantOrderId());	
		pt.setMerchantTimestamp(paymentRequest.getMerchantTimestamp());
		pt.setAmount(paymentRequest.getAmount());
		pt.setSuccesUrl(paymentRequest.getSuccesUrl());
		pt.setFailedUrl(paymentRequest.getFailedUrl());
		pt.setErrorUrl(paymentRequest.getErrorUrl());
		pt = paymentRepository.save(pt);
		
		return new AcquirerResponse(1,"https://localhost:11000/payment.html?id=" + Long.toString(pt.getId()));
	}
	
	public String executePayment(ExecutePayment ep) {
		
		UserAccount payer = userAccountRepository.findOneByPAN(ep.getPayerPan());
		Payment pt = paymentRepository.findOneById(ep.getPaymentId());
		
		if(payer != null) {
			
			if(payer.getAvailableFunds() > pt.getAmount()) {
				
				pt.setPayeer(payer);
				pt.setStatus(PaymentStatus.SUCCESSFUL);
				payer.setAvailableFunds(payer.getAvailableFunds()-pt.getAmount());
				payer.setReservedFunds(pt.getAmount());
				paymentRepository.save(pt);
				userAccountRepository.save(payer);
				return pt.getSuccesUrl() + "?merchantOrderId="+ Long.toString(pt.getMerchantOrderId());
				
			}else {
				pt.setStatus(PaymentStatus.FAILED);
				paymentRepository.save(pt);
				return pt.getFailedUrl() + "?merchantOrderId="+ Long.toString(pt.getMerchantOrderId());
			}
		} else {
			PCCRequest pccRequest = new PCCRequest();
			pccRequest.setAcquirerTimestamp(new Date());
			pccRequest.setCardHolderName(ep.getCardHolderName());
			pccRequest.setExpirationDate(ep.getExpirationDate());
			pccRequest.setPayerPan(ep.getPayerPan());
			pccRequest.setSecurityCode(ep.getSecurityCode());
			pccRequest.setAmount(pt.getAmount());
			pccRepository.save(pccRequest);
			
			ResponseEntity<PCCResponse> response = restTemplate.postForEntity(PCC_URL + "/createPayment", pccRequest, PCCResponse.class);
			
			if(response.getBody().getStatus().equals("success")) {
				//userAccountRepository.save(response.getBody().getUser());
				pt.setPayeer(response.getBody().getUser());
				pt.setStatus(PaymentStatus.SUCCESSFUL);
				paymentRepository.save(pt);
				return pt.getSuccesUrl() + "?merchantOrderId="+ Long.toString(pt.getMerchantOrderId());
			} else {
				pt.setStatus(PaymentStatus.FAILED);
				paymentRepository.save(pt);
				return pt.getFailedUrl() + "?merchantOrderId="+ Long.toString(pt.getMerchantOrderId());
			}
			
			
		}
		
	}
}
