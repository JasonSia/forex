package com.team2.forex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.team2.forex.entity.Order;
import com.team2.forex.repository.TradersOrderRepository;

@Component
public class OrderService {

	@Autowired
	TradersOrderRepository orderRp;
	
	public List<Order> getOpenOrder(){
		return orderRp.getOpenOrders();
	}
	
	public List<Order> getClosedOrder(){
		return orderRp.getClosedOrders();
	}
	
	
}
