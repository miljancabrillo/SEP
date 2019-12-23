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
public class UserAccount {

	@Id
	@GeneratedValue
	private long id;
	private long password;
	private double availableFunds;
	private double reservedFunds;
	private String currency;
	private String PAN;
	private long creditCardSecurityNumber;
	private String cardHolderName;
	private Date expirationDate;
		
}
