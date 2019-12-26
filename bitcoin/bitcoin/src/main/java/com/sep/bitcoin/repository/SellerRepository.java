package com.sep.bitcoin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.bitcoin.model.Seller;

public interface SellerRepository extends JpaRepository<Seller,Long> {

	@SuppressWarnings("unchecked")
	public Seller save(Seller seller);
	
	public Seller findOneById(long id);
	
}

