package com.sep.bank.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seller {

	@Id
	private long id;
	private String merchantId;
	private String merchantPassword;
	private String email;
	
}
