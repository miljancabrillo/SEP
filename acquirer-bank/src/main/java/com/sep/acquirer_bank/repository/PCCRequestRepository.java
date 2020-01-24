package com.sep.acquirer_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.acquirer_bank.model.PCCRequest;

public interface PCCRequestRepository extends JpaRepository<PCCRequest, Long> {

}
