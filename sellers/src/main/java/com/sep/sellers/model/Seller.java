package com.sep.sellers.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name= "seller_payment_type", 
             joinColumns = { @JoinColumn(name = "seller_id") }, 
             inverseJoinColumns = { @JoinColumn(name = "payment_type_id") })
	private List<PaymentType> paymentTypes;
	
	public void addPaymentType(PaymentType paymentType) {
		paymentTypes.add(paymentType);
	}
}
