package com.sep.acquirer_bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcquirerResponse {

	private long paymentId;
	private String paymentUrl;
	
}
