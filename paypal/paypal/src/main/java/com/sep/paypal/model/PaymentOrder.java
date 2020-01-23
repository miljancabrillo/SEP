package com.sep.paypal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

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
	private String id;
	
	@Column(unique = true, length = 150)
	private String paymentId;
	
	@ManyToOne
	private Seller seller;
	
	private String payerId;
	private double price;
	private String currency;
	private String method;
	private String intent;
	private String description;
	private PaymentOrderStatus status = PaymentOrderStatus.CREATED;
	
}
