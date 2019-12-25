package com.sep.bitcoin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sep.bitcoin.model.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}
