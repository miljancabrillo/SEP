package com.sep.paypal.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDTO {

	private long sellerId;
	
	private String name;
	private String description;
	private String type;         //tip oznacava da li je plan koncan i beskonacan u smislu ponavljanja upalata
	
	private String frequency;		//period uplacivanja mjesec, godina itd
	private String frequencyIntrval;		//period ponavljanja uplate npa DVA mjeseca
	private String cycles;
	
	private String amount;
	private String currency;
	private String confirmationURL;
	
}
