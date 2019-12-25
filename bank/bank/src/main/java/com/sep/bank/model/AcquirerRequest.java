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
public class AcquirerRequest {
	
	private String merchantId;
	private String merchantPassword;
	private float amount;
	//id objekta paymentOrder koji cuvamo u bazi
	private long merchantOrderId;
	//timestamp paymentOrder objekta
	private Date merchantTimestamp;
	private String succesUrl;
	private String failedUrl;
	private String errorUrl;

}
