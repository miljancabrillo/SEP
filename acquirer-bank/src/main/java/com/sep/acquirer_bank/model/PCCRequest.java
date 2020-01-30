package com.sep.acquirer_bank.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PCCRequest {

	@Id
	@GeneratedValue
	private long acquirerOrderId;
	private Date acquirerTimestamp;
	private String payerPan;
	private int securityCode;
	private String cardHolderName;
	private String expirationDate;
	private double amount;
	
}
