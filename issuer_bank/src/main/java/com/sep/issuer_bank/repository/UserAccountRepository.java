package com.sep.issuer_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.issuer_bank.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	UserAccount findOneByPAN(String pan);
}
