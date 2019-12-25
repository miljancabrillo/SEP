package com.sep.acquirer_bank.model;


import javax.persistence.Column;
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
	private String password;
	private double availableFunds;
	private double reservedFunds;
	private String currency;
	@Column(unique = true, length = 10)
	private String PAN;
	private long creditCardSecurityNumber;
	private String cardHolderName;
	private String expirationDate;
		
}
