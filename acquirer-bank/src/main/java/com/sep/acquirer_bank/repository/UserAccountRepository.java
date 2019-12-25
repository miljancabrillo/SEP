
package com.sep.acquirer_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.acquirer_bank.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	UserAccount findOneById(long id);

	UserAccount findOneByPAN(String pan);
	
}

