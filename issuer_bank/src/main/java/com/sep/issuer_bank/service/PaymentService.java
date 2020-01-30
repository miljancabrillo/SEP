package com.sep.issuer_bank.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.sep.issuer_bank.model.PCCRequest;
import com.sep.issuer_bank.model.PCCResponse;
import com.sep.issuer_bank.model.UserAccount;
import com.sep.issuer_bank.repository.PCCResponseRepository;
import com.sep.issuer_bank.repository.UserAccountRepository;

@Service
public class PaymentService {

	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	PCCResponseRepository pccResponseRepository;
	
	public PCCResponse executePayment(PCCRequest pccRequest) {
		
		UserAccount payer = userAccountRepository.findOneByPAN(pccRequest.getPayerPan());
		PCCResponse pccResponse = new PCCResponse();
		pccResponse.setAcquirerOrderId(pccRequest.getAcquirerOrderId());
		pccResponse.setAcquirerTimestamp(pccRequest.getAcquirerTimestamp());
		pccResponse.setIssuerTimestamp(new Date());
		pccResponseRepository.save(pccResponse);
		if(payer != null) {
			if(payer.getAvailableFunds() > pccRequest.getAmount()) {
				
				payer.setAvailableFunds(payer.getAvailableFunds()-pccRequest.getAmount());
				payer.setReservedFunds(pccRequest.getAmount());
				userAccountRepository.save(payer);
				pccResponse.setUser(payer);
				pccResponse.setStatus("success");
				pccResponseRepository.save(pccResponse);
				return pccResponse;
				
			}else {
				pccResponse.setStatus("failed");
				pccResponseRepository.save(pccResponse);
				return pccResponse;
			}
		} else {
			pccResponse.setStatus("failed");
			pccResponseRepository.save(pccResponse);
			return pccResponse;
		}
	}
}
