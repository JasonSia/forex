package com.team2.forex.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.team2.forex.entity.Order;
import com.team2.forex.repository.MarketOrderRepository;

//@Component
@Repository
public class MarketOrderRepository {
	
    private final static Logger logger = LoggerFactory.getLogger(MarketOrderRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public MarketOrderRepository(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public String PlaceMarketOrder(Order order) {
      jdbcTemplate.update("insert into ORDERLIST(ORDERTYPE,CURRENCYBUY,CURRENCYSELL,SIZE,PREFERREDPRICE,EXECUTEDPRICE,STATUS,GOODTILLDATE,SUBMITTEDTIME,EXECUTEDTIME,USERID) "
      		+ "values (?,?,?,?,?,?,?,?,?,?,?)", 
    		  	order.getOrderType(), order.getCurrencyBuy(), order.getCurrencySell(), order.getSize(), null, null, order.getStatus(), null, 
    		  	order.getSubmittedTime(), null, order.getUserId()
    		  	);
      return order.getOrderType();
    }
    
    
    
}