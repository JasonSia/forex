package com.team2.forex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.team2.forex.entity.Order;
import com.team2.forex.repository.LimitOrderRepository;

@Component
public class LimitOrderService {

	@Autowired
	LimitOrderRepository limitOrderRp;
	
	public Order placeLimitOrder(Order lmtOrder){
		return limitOrderRp.PlaceLimitOrder(lmtOrder);
		
	}
	
	public String cancelLimitOrder(String orderNumber){
		Order order=limitOrderRp.checkLimitOrderExists(orderNumber);
		if(order!=null){
		 // if(order.getStatus().equals(Status.))
			System.out.println("in los: order found, preferredPrice: "+order.getPreferredPrice());
	      return "success";
		}
		else
		{
		    System.out.println("in los: order not found");
			return "order with orderId not found";
		}
	}
	
}

