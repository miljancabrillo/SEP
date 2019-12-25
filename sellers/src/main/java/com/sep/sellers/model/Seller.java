package com.sep.sellers.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;


@Entity
public class Seller {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name= "seller_payment_type", 
             joinColumns = { @JoinColumn(name = "seller_id") }, 
             inverseJoinColumns = { @JoinColumn(name = "payment_type_id") })
	private List<PaymentType> paymentTypes;
	
	public Seller(long id, String name, List<PaymentType> paymentTypes) {
		super();
		this.id = id;
		this.name = name;
		this.paymentTypes = paymentTypes;
	}
	
	public Seller() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(List<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
}
