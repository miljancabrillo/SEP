package com.sep.bank.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class PaymentRequest {
	
	private String merchantId;
	private String merchantPassword;
	private float amount;
	private long merchantOrderId;
	private Date merchantTimestamp;
	private String succesUrl;
	private String failedUrl;
	private String errorUrl;

}