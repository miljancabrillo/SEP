package com.sep.issuer_bank.model;

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
public class PCCResponse {

	@Id
	@GeneratedValue
	private long issuerOrderId;
	private long acquirerOrderId;
	private Date acquirerTimestamp;
	private Date issuerTimestamp;
	@ManyToOne
	private UserAccount user;
	private String status;
}
