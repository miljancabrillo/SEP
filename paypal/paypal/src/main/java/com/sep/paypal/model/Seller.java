package com.sep.paypal.model;

import javax.persistence.Entity;
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
public class Seller {

	@Id
	private long id;
	private String paypalClientId;
	private String paypalSecret;
	private String email;
	
}
