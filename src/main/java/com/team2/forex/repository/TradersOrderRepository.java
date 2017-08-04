package com.team2.forex.repository;

import java.sql.Timestamp;
import java.util.List;

import com.team2.forex.entity.Order;

public interface TradersOrderRepository {
	//get open orders (unfilled & partially filled)
	public List<Order> getOpenOrders ();
	public List<Order> getClosedOrders (Timestamp startDate, Timestamp endDate);
}
