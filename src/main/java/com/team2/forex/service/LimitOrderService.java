package com.team2.forex.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;
import com.team2.forex.repository.LimitOrderRepository;
import com.team2.forex.repository.OrderAuditRepository;

@Component
public class LimitOrderService {

	@Autowired
	LimitOrderRepository limitOrderRp;
	
	@Autowired
	OrderAuditRepository orderAuditRp;
	
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
				return "ERROR: The order has already filled or cancelled.";
		    }
			else
			{
				System.out.println("ok with order owner and status, start cancel");
				order.setStatus(Status.CANCELLED);
				String result=limitOrderRp.cancelOrder(order.getOrderId());
				Timestamp currentTime=new Timestamp(System.currentTimeMillis());
				orderAuditRp.updateOrderAudit(order, currentTime);
				return result;
			}
		}
		else
		{
		    System.out.println("in los: order not found");
			return "ERROR: The order number is incorret.";
		}
	
	}
	
}
