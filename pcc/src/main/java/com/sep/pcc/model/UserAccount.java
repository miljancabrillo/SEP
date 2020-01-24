package com.sep.pcc.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

	private long id;
	private String password;
	private double availableFunds;
	private double reservedFunds;
	private String currency;
	private String PAN;
	private long creditCardSecurityNumber;
	private String cardHolderName;
	private String expirationDate;
		
}
