package com.sep.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.bank.model.Seller;

public interface SellersRepository extends JpaRepository<Seller, Long> {

	public Seller findOneById(long id);
	
}
