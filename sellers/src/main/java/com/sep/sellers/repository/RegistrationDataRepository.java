package com.sep.sellers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.sellers.model.RegistrationData;

public interface RegistrationDataRepository extends JpaRepository<RegistrationData, Long> {

	public RegistrationData findOneById(long id);
	
}
