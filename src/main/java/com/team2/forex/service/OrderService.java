package com.team2.forex.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.team2.forex.entity.Order;
import com.team2.forex.repository.TradersOrderRepository;
import com.team2.forex.util.DateTimeUtil;

@Component
public class OrderService {

	@Autowired
	TradersOrderRepository orderRp;
	
	public List<Order> getOpenOrder(){
		return orderRp.getOpenOrders();
	}
	 
	public List<Order> getClosedOrder(String dateRange) throws ParseException, JSONException{
		JSONObject obj = new JSONObject(dateRange);
		Timestamp tsStart = DateTimeUtil.stringToTimestamp(obj.getString("startDate"));
		Timestamp tsEnd = DateTimeUtil.stringToTimestamp(obj.getString("endDate"));
		return orderRp.getClosedOrders(tsStart, tsEnd);
	
	}
	
	
}
