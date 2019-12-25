package com.sep.acquirer_bank.model;

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
	
	@ManyToOne
	private UserAccount payee;
	
	@ManyToOne
	private UserAccount payeer;
	
	private double amount;
	private long merchantOrderId;
	private Date merchantTimestamp;
	private PaymentStatus status;
	private String succesUrl;
	private String failedUrl;
	private String errorUrl;
}
