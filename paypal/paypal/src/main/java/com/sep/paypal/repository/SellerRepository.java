package com.sep.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.paypal.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {

	public Seller findOneById(long id);
	
}
