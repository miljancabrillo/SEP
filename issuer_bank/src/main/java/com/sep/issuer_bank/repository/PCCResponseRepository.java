package com.sep.issuer_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.issuer_bank.model.PCCResponse;

public interface PCCResponseRepository extends JpaRepository<PCCResponse, Long> {

}
