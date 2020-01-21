package com.sep.sellers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.sellers.model.Seller;

public interface SellersRepository extends JpaRepository<Seller, Long> {

	public Seller findOneById(long id);
	
}
