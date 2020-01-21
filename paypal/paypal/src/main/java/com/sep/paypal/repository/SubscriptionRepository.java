package com.sep.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.paypal.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
