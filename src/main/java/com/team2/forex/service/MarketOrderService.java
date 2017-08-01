package com.team2.forex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.forex.entity.Order;
import com.team2.forex.repository.MarketOrderRepository;

@Service
public class MarketOrderService {

	@Autowired
	MarketOrderRepository marketOrderRp;
	
	public String placeMarketOrder(Order mktOrder){
		return null;
	}
	
}
