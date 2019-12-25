package com.sep.bitcoin.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
public class Order {

	@Id
	private long merchantOrderId;

	private LocalDateTime merchantTimestamp;

	private String payerId;

	private Integer quantity;

	private Double price;

	private String currency;

	private String buyerFirstName;

	private String buyerLastName;

	private String buyerEmail;

	private String merchandise;

	private Boolean executed;

	private String bankUrl;

	private String merchantPassword;

	private String merchantId;

	private String productType;

	private Long productId;

	// Dodala sam polje status jer mi to vraca btc - paid, pending...
	private Integer idBitcoin;
	private String status;

	public Order() {
		super();
	}

	public Order(LocalDateTime merchantTimestamp, String payerId, Integer quantity, Double price,
			String currency, String buyerFirstName, String buyerLastName, String buyerEmail,
			String merchandise, Boolean executed) {
		this.merchantTimestamp = merchantTimestamp;
		this.payerId = payerId;
		this.quantity = quantity;
		this.price = price;
		this.currency = currency;
		this.buyerFirstName = buyerFirstName;
		this.buyerLastName = buyerLastName;
		this.buyerEmail = buyerEmail;
		this.merchandise = merchandise;
		this.executed = executed;
	}

	@Override
	public String toString() {
		return "Order{" + "merchantOrderId=" + merchantOrderId + ", merchantTimestamp=" + merchantTimestamp
				+ ", payerId='" + payerId + '\'' + ", quantity=" + quantity + ", price="
				+ price + ", currency='" + currency + '\'' + ", buyerFirstName='" + buyerFirstName
				+ '\'' + ", buyerLastName='" + buyerLastName + '\'' + ", buyerEmail='" + buyerEmail + '\''
				+ ", merchandise='" + merchandise + '\'' + ", executed=" + executed + '}';
	}

	public long getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(long merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public LocalDateTime getMerchantTimestamp() {
		return merchantTimestamp;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setMerchantTimestamp(LocalDateTime merchantTimestamp) {
		this.merchantTimestamp = merchantTimestamp;
	}

	public String getPayerId() {
		return payerId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getBuyerFirstName() {
		return buyerFirstName;
	}

	public void setBuyerFirstName(String buyerFirstName) {
		this.buyerFirstName = buyerFirstName;
	}

	public String getBuyerLastName() {
		return buyerLastName;
	}

	public void setBuyerLastName(String buyerLastName) {
		this.buyerLastName = buyerLastName;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(String merchandise) {
		this.merchandise = merchandise;
	}

	public Boolean getExecuted() {
		return executed;
	}

	public void setExecuted(Boolean executed) {
		this.executed = executed;
	}

	public Integer getIdBitcoin() {
		return idBitcoin;
	}

	public void setIdBitcoin(Integer idBitcoin) {
		this.idBitcoin = idBitcoin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBankUrl() {
		return bankUrl;
	}

	public void setBankUrl(String bankUrl) {
		this.bankUrl = bankUrl;
	}

	public String getMerchantPassword() {
		return merchantPassword;
	}

	public void setMerchantPassword(String merchantPassword) {
		this.merchantPassword = merchantPassword;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}
