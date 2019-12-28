package com.sep.bitcoin.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentOrder {
	
    @Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 100)
	@JsonProperty("order_id")
	private String orderId;
	
	private long id;		//id koji se dobije od coingete-a
	
	@JsonProperty("price_amount")
	private double priceAmount;
	
	@JsonProperty("price_currency")
	private String priceCurrency;
	
	@JsonProperty("receive_currency")
	private String receiveCurrency;
	
	private String title;
	
	private String description;
	
	@JsonProperty("callback_url")
	private String callbackUrl;
	
	@JsonProperty("cancel_url")
	private String cancelUrl;
	
	@JsonProperty("success_url")
	private String succesUrl;
	
	private String token;
	
	@ManyToOne
	private Seller payee;
	
	private String status;
	
	@JsonProperty("created_at")
	private Date createdAt;
	
	@JsonProperty("payment_url")
	private String paymentUrl;
}
