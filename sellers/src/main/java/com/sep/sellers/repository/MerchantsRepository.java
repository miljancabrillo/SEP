package com.sep.sellers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.sellers.model.Merchant;

public interface MerchantsRepository extends JpaRepository<Merchant, Long> {

	
	
}
