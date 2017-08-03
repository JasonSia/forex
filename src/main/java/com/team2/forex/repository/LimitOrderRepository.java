package com.team2.forex.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import com.team2.forex.entity.Order;

public interface LimitOrderRepository{
	
	public Order PlaceLimitOrder(Order order);
	public Order getOrderId(String orderType, String currencyBuyInput, String currencySellInput, int size, double preferredPrice, Timestamp goodTillDate, Timestamp submittedTime, String userId);
	Order LimitgetOrder(String orderType, String currencyBuyInput, String currencySellInput, int size,
			double preferredprice, Timestamp goodTillDate, Timestamp submittedTime,  String userId) throws EmptyResultDataAccessException;
	public Order checkLimitOrderExists(String orderNumber);
	public String cancelOrder(int orderId);
	public Order[] matchLimitOrder();
	public List<Order> getAllLimitOrder();
}