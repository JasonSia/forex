package com.team2.forex.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;
import com.team2.forex.repository.LimitOrderRepository;
import com.team2.forex.repository.OrderAuditRepository;

@Service
public class ForexMatchingService {
	@Autowired
	private LimitOrderRepository limitOrderRepository;
	
	@Autowired
	private OrderAuditRepository orderAuditRepository;
	
	public void processLimitOrderMatching(){
		boolean isRun = true;
		
		while(isRun){
			try{
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
					limitOrderRepository.updateLimitOrder(buyOrder);
					limitOrderRepository.updateLimitOrder(sellOrder);
					
					orderAuditRepository.createOrderAudit(buyOrder, ts);
					orderAuditRepository.createOrderAudit(sellOrder, ts);
				}else{
					//create new split filled order
					Order splitOrder = new Order();
					splitOrder.setCurrencyBuy(buyOrder.getCurrencyBuy().name());
					splitOrder.setCurrencySell(buyOrder.getCurrencySell().name());
					splitOrder.setGoodTillDate(buyOrder.getGoodTillDate());
					splitOrder.setOrderNumber(buyOrder.getOrderNumber());
					splitOrder.setOrderType(buyOrder.getOrderType());
					splitOrder.setPreferredPrice(buyOrder.getPreferredPrice());
					splitOrder.setSubmittedTime(buyOrder.getSubmittedTime());
					splitOrder.setUserId(buyOrder.getUserId());
					
					//set executed price
					sellOrder.setExecutedPrice(sellOrder.getPreferredPrice());
					splitOrder.setExecutedPrice(sellOrder.getPreferredPrice());
					
					//set updated lot size
					buyOrder.setSize(buyOrder.getSize() - sellOrder.getSize());
					splitOrder.setSize(sellOrder.getSize());
					
					//set status
					buyOrder.setStatus(Status.PARTIALLYFILLED);
					sellOrder.setStatus(Status.FILLED);
					splitOrder.setStatus(Status.FILLED);
					
					//set executed time
					Timestamp ts = new Timestamp(new Date().getTime());
					sellOrder.setExecutedTime(ts);
					splitOrder.setExecutedTime(ts);
					
					//execute order
					limitOrderRepository.updateLimitOrder(buyOrder);
					limitOrderRepository.updateLimitOrder(sellOrder);
					limitOrderRepository.PlaceLimitOrder(splitOrder);
					
					orderAuditRepository.createOrderAudit(buyOrder, ts);
					orderAuditRepository.createOrderAudit(sellOrder, ts);
					orderAuditRepository.createOrderAudit(splitOrder, ts);
				}
			}catch(EmptyResultDataAccessException e){
				isRun = false;
			}
		}
	}
	
	public void cleanUpLimitOrder(){
		//get all limit order to clean up
		List<Order> orderList =  limitOrderRepository.getAllLimitOrderBeforeCurrentTime();
		
		for(Order order: orderList){
			//set to expire
			order.setStatus(Status.EXPIRED);
			limitOrderRepository.updateLimitOrder(order);
			
			//add audit record
			Timestamp ts = new Timestamp(new Date().getTime());
			orderAuditRepository.createOrderAudit(order, ts);
		}
	}
}
