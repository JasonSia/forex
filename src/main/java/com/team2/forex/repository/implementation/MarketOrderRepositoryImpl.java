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

//@Component
@Repository
public class MarketOrderRepositoryImpl implements MarketOrderRepository{
	
    private final static Logger logger = LoggerFactory.getLogger(MarketOrderRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public MarketOrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    @Transactional
    public Order PlaceAndExecuteMarketOrder(Order order, double price) {
        order.setExecutedTime(new Timestamp(System.currentTimeMillis()));
        order.setStatus(Status.FILLED);
    	int orderId=jdbcTemplate.update("insert into ORDERLIST(ORDERTYPE,CURRENCYBUY,CURRENCYSELL,SIZE,PREFERREDPRICE,EXECUTEDPRICE,STATUS,GOODTILLDATE,SUBMITTEDTIME,EXECUTEDTIME,USERID,ORDERNUMBER) "
      		+ "values (?,?,?,?,?,?,?,?,?,?,?,?)", 
    		  	order.getOrderType(), order.getCurrencyBuy().name(), order.getCurrencySell().name(), order.getSize(), null, price, order.getStatus().name(), null, 
    		  	order.getSubmittedTime(), order.getExecutedTime(), order.getUserId(), order.getOrderNumber());
        Order orderReturned=getOrder(order.getOrderType(), order.getCurrencyBuy().name(), order.getCurrencySell().name(), order.getSize(), order.getSubmittedTime(), order.getUserId(), order.getOrderNumber());
        System.out.println("Order returned ID: "+orderReturned.getOrderId());
        System.out.println("Order returned size: "+orderReturned.getSize());
        System.out.println("Order returned order number:  "+orderReturned.getOrderNumber());
        
        return orderReturned;
    }
    
    @Override
	@Transactional(readOnly=true)
	public Order getOrder(String orderType, String currencyBuyInput, String currencySellInput, int size, Timestamp submittedTime, String userId, String orderNumber) throws EmptyResultDataAccessException{
		return jdbcTemplate.queryForObject("SELECT orderId, currencyBuy, currencySell, executedPrice, submittedTime, executedTime, size, orderNumber, orderType, preferredPrice, status, goodTillDate, userId FROM orderList "
				+ "WHERE orderType = ? AND currencyBuy = ? AND currencySell = ? AND "
				+ "size = ? AND submittedTime = ? AND userId = ? AND orderNumber = ? "
				+ "ORDER BY submittedTime DESC LIMIT 1", 
				new Object[]{orderType, currencyBuyInput, currencySellInput, size, submittedTime, userId, orderNumber}, 
				new OrderRowMapper());
	}
}

class OrderRowMapper implements RowMapper<Order>{

	@Override
	public Order mapRow(ResultSet rs, int i) throws SQLException {
		Order order=new Order();
		order.setOrderId(rs.getInt("orderId"));
		order.setCurrencyBuy(rs.getString("currencyBuy"));
		order.setCurrencySell(rs.getString("currencySell"));
		order.setExecutedPrice(rs.getDouble("executedPrice"));
		order.setSubmittedTime(rs.getTimestamp("submittedTime"));
		order.setExecutedTime(rs.getTimestamp("executedTime"));
		order.setSize(rs.getInt("size"));
		order.setOrderNumber(rs.getString("orderNumber"));
		order.setOrderType(rs.getString("orderType"));
		order.setPreferredPrice(rs.getDouble("preferredPrice"));
		order.setStatus(Status.valueOf(rs.getString("status")));
		order.setGoodTillDate(rs.getTimestamp("goodTillDate"));
		order.setUserId(rs.getString("userId"));
		System.out.println("order id received in order row mapper is: "+order.getOrderId());
		return order;
	}
}
	
