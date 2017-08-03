package com.team2.forex.repository.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.team2.forex.entity.Currency;
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;
import com.team2.forex.repository.MarketOrderRepository;
import com.team2.forex.repository.OrderAuditRepository;

//@Component
@Repository
public class OrderAuditRepositoryImpl implements OrderAuditRepository{
	
    private final static Logger logger = LoggerFactory.getLogger(MarketOrderRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public OrderAuditRepositoryImpl(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    @Transactional
    public void createOrderAudit(Order order, Timestamp modifiedTime){
    	System.out.println("Order passed is: "+order.getOrderId());
    	if(order.getOrderType().equalsIgnoreCase("LIMIT")){
    	jdbcTemplate.update("insert into ORDERAUDIT(ORDERID,ORDERNUMBER,ORDERTYPE,CURRENCYBUY,CURRENCYSELL,SIZE,PREFERREDPRICE,EXECUTEDPRICE,STATUS,GOODTILLDATE,SUBMITTEDTIME,EXECUTEDTIME,USERID,MODIFIEDTIME) "
          		+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
        		  	order.getOrderId(), order.getOrderNumber(), order.getOrderType(), order.getCurrencyBuy().name(), order.getCurrencySell().name(), order.getSize(), order.getPreferredPrice(), order.getExecutedPrice(), order.getStatus().name(), order.getGoodTillDate(), 
        		  	order.getSubmittedTime(), order.getExecutedTime(), order.getUserId(), modifiedTime);
    	System.out.println("Order Audit row created!");
    }
    	else if(order.getOrderType().equalsIgnoreCase("MARKET")){
        	jdbcTemplate.update("insert into ORDERAUDIT(ORDERID,ORDERNUMBER,ORDERTYPE,CURRENCYBUY,CURRENCYSELL,SIZE,PREFERREDPRICE,EXECUTEDPRICE,STATUS,GOODTILLDATE,SUBMITTEDTIME,EXECUTEDTIME,USERID,MODIFIEDTIME) "
              		+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
            		  	order.getOrderId(), order.getOrderNumber(), order.getOrderType(), order.getCurrencyBuy().name(), order.getCurrencySell().name(), order.getSize(), null, order.getExecutedPrice(), order.getStatus().name(), null, 
            		  	order.getSubmittedTime(), order.getExecutedTime(), order.getUserId(), modifiedTime);
        	System.out.println("Order Audit row created!");
    }
}
    
    @Override
    @Transactional
    public void updateOrderAudit(Order order, Timestamp modifiedTime){
    	System.out.println("Order passed for cancellation is: "+order.getOrderId());
    	if(order.getOrderType().equalsIgnoreCase("LIMIT")){
    	jdbcTemplate.update("insert into ORDERAUDIT(ORDERID,ORDERNUMBER,ORDERTYPE,CURRENCYBUY,CURRENCYSELL,SIZE,PREFERREDPRICE,EXECUTEDPRICE,STATUS,GOODTILLDATE,SUBMITTEDTIME,EXECUTEDTIME,USERID,MODIFIEDTIME) "
          		+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
        		  	order.getOrderId(), order.getOrderNumber(), order.getOrderType(), order.getCurrencyBuy().name(), order.getCurrencySell().name(), order.getSize(), order.getPreferredPrice(), order.getExecutedPrice(), order.getStatus().name(), order.getGoodTillDate(), 
        		  	order.getSubmittedTime(), order.getExecutedTime(), order.getUserId(), modifiedTime);
    	System.out.println("Order Audit row created!");
    }
}
}