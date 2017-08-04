package com.team2.forex.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.team2.forex.entity.Order;
import com.team2.forex.repository.HistoricalTradeDataRepository;
import com.team2.forex.repository.MarketOrderRepository;
import com.team2.forex.repository.OrderAuditRepository;

@Component
public class OrderAuditService {

	@Autowired
	OrderAuditRepository orderAuditRp;
	
	//use only for successful trade i.e. Status changed to Filled
	public void createOrderAudit(Order order, Timestamp modifiedTime){
		
	}
	
	//use only for cancelled trade i.e. Status changed to Cancelled
	public void updateOrderAudit(Order order){
		
	}
	
}
