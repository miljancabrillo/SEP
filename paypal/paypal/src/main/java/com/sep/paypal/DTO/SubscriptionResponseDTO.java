package com.sep.paypal.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDTO {

	private String subscriptionId;
	private String paymentUrl;
	private String cancelUrl;
	
}
