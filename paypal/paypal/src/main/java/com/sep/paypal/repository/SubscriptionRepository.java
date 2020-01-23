package com.sep.paypal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sep.paypal.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	public Subscription findOneByAggrementToken(String token);
	
	@Query("select sub from Subscription sub where sub.status = :status")
	public List<Subscription> getSubscriptionsByStatus(@Param("status") String status);
	
}
