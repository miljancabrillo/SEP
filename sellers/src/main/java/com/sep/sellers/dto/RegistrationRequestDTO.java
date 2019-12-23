package com.sep.sellers.dto;

public class RegistrationRequestDTO {

	private long sellerId;
	private String registrationUrl;
	
	public RegistrationRequestDTO(long sellerId, String registrationUrl) {
		super();
		this.sellerId = sellerId;
		this.registrationUrl = registrationUrl;
	}
	
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long merchantId) {
		this.sellerId = merchantId;
	}
	public String getRegistrationUrl() {
		return registrationUrl;
	}
	public void setRegistrationUrl(String registrationUrl) {
		this.registrationUrl = registrationUrl;
	}
	
	
	
}
