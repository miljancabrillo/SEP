package com.sep.paypal.model;

public class PaymentRequest {
	
	private long sellerId;
	private float price;
	private String currency;
	
	public PaymentRequest(long sellerId, float price, String currency) {
		super();
		this.sellerId = sellerId;
		this.price = price;
		this.currency = currency;
	}
	
	public PaymentRequest() {
		super();
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
