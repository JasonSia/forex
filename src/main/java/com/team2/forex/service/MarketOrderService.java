package com.team2.forex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.team2.forex.entity.Order;
import com.team2.forex.repository.MarketOrderRepository;

@Component
public class MarketOrderService {

	@Autowired
	MarketOrderRepository marketOrderRp;
	
	public int placeMarketOrder(Order mktOrder){
		return marketOrderRp.PlaceMarketOrder(mktOrder);
		
	}
	
}
