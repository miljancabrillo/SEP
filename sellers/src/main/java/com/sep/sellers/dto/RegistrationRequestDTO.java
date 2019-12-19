package com.sep.sellers.dto;

public class RegistrationRequestDTO {

	private long merchantId;
	private String registrationUrl;
	
	public RegistrationRequestDTO(long merchantId, String registrationUrl) {
		super();
		this.merchantId = merchantId;
		this.registrationUrl = registrationUrl;
	}
	
	public long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	public String getRegistrationUrl() {
		return registrationUrl;
	}
	public void setRegistrationUrl(String registrationUrl) {
		this.registrationUrl = registrationUrl;
	}
	
	
	
}
