package com.team2.forex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.team2.forex.entity.Order;
import com.team2.forex.repository.LimitOrderRepository;

@Component
public class LimitOrderService {

	@Autowired
	LimitOrderRepository limitOrderRp;
	
	public int placeLimitOrder(Order lmtOrder){
		return limitOrderRp.PlaceLimitOrder(lmtOrder);
		
	}
	
}

