package com.sep.acquirer_bank.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PCCResponse {

	private long acquirerOrderId;
	private Date acquirerTimestamp;
	private long issuerOrderId;
	private Date issuerTimestamp;
	private UserAccount user;
	private String status;
}
