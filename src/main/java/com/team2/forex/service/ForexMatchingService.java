package com.team2.forex.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;
import com.team2.forex.repository.LimitOrderRepository;

@Service
public class ForexMatchingService {
	@Autowired
	private LimitOrderRepository limitOrderRepository;
	
	public void processLimitOrderMatching(){
		//find matching orders
		Order[] orders = limitOrderRepository.matchLimitOrder();
		
		//get orders
		Order buyOrder = orders[0];
		Order sellOrder = orders[1];
		
		//process orders
		if(buyOrder.getSize() == sellOrder.getSize()){
			//set executed price
			buyOrder.setExecutedPrice(sellOrder.getPreferredPrice());
			sellOrder.setExecutedPrice(sellOrder.getPreferredPrice());
			
			//set status
			buyOrder.setStatus(Status.FILLED);
			sellOrder.setStatus(Status.FILLED);
			
			//set executed time
			Timestamp ts = new Timestamp(new Date().getTime());
			buyOrder.setExecutedTime(ts);
			sellOrder.setExecutedTime(ts);
			
			//execute orders
			
		}else{
			
		}
		
	}
	
	public void cleanUpLimitOrder(){
		
	}
}
