package com.team2.forex.repository;

import java.util.List;

import com.team2.forex.entity.Order;

public interface TradersOpenOrderRepository {
	//get open orders (unfilled & partially filled)
	public List<Order> getOpenOrders ();
}