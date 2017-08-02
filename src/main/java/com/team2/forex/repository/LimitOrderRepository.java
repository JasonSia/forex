package com.team2.forex.repository;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.entity.Order;
import com.team2.forex.repository.MarketOrderRepository;

public interface LimitOrderRepository{
	
	public int PlaceLimitOrder(Order order);
	public Order getOrderId(String orderType, String currencyBuyInput, String currencySellInput, int size, double preferredPrice, Timestamp goodTillDate, Timestamp submittedTime, String userId);
	Order LimitgetOrderId(String orderType, String currencyBuyInput, String currencySellInput, int size,
			double preferredprice, Timestamp goodTillDate, Timestamp submittedTime,  String userId) throws EmptyResultDataAccessException;
}