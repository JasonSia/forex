package com.team2.forex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;
import com.team2.forex.repository.LimitOrderRepository;

@Component
public class LimitOrderService {

	@Autowired
	LimitOrderRepository limitOrderRp;
	
	public Order placeLimitOrder(Order lmtOrder){
		return limitOrderRp.PlaceLimitOrder(lmtOrder);
		
	}
	
	public String cancelLimitOrder(String orderNumber, String loginUserId){
		Order order=limitOrderRp.checkLimitOrderExists(orderNumber);
		System.out.println("Order result =" + order);
		if(order!=null){
			if (!order.getUserId().equals(loginUserId)) {
				System.out.println("mismatch in login user and order user");
		      return "ERROR: The login ID mismatch with order owner, please try again.";
			}
			//else if (limitOrderRp.checkLimitOrderExists(orderNumber) = null)
			else if(!(order.getStatus().equals(Status.PARTIALLYFILLED) || order.getStatus().equals(Status.NOTFILLED))) {
				System.out.println("in los: order found, status: " +order.getStatus());
				return "ERROR: The order has already filled or canceled.";
		    }
			else
			{
				System.out.println("ok with order owner and status, start cancel");
				order.setStatus(Status.CANCELLED);
				return limitOrderRp.cancelOrder(order.getOrderId());
			}
		}
		else
		{
		    System.out.println("in los: order not found");
			return "ERROR: The order number is incorret.";
		}
	
	}
	
}
