package com.sep.bank.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {
	
	@Id
	@GeneratedValue
	private long id;
	
	private Date timestamp;
	
	@ManyToOne
	private Seller seller;
	
	private double amount;
	
	private PaymentStatus status = PaymentStatus.CREATED;

}
