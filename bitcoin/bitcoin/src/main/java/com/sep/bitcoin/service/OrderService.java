package com.sep.bitcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sep.bitcoin.model.Order;
import com.sep.bitcoin.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	public Order createOrderBitcoin(Order o) {
		 return orderRepository.save(o);
	}
}
