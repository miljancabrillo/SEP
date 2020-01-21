package com.sep.sellers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.sellers.model.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

	PaymentType findOneById(long id);
	
}
