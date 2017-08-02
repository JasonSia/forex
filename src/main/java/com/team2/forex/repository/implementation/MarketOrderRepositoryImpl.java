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
    public int PlaceMarketOrder(Order order) {
      int orderId=jdbcTemplate.update("insert into ORDERLIST(ORDERTYPE,CURRENCYBUY,CURRENCYSELL,SIZE,PREFERREDPRICE,EXECUTEDPRICE,STATUS,GOODTILLDATE,SUBMITTEDTIME,EXECUTEDTIME,USERID) "
      		+ "values (?,?,?,?,?,?,?,?,?,?,?)", 
    		  	order.getOrderType(), order.getCurrencyBuyInput(), order.getCurrencySellInput(), order.getSize(), null, null, order.getStatus().name(), null, 
    		  	order.getSubmittedTime(), null, order.getUserId());
      return getOrderId(order.getOrderType(), order.getCurrencyBuyInput(), order.getCurrencySellInput(), order.getSize(), order.getSubmittedTime(), order.getUserId()).getOrderId();
    }
    
    @Override
	@Transactional(readOnly=true)
	public Order getOrderId(String orderType, String currencyBuyInput, String currencySellInput, int size, Timestamp submittedTime, String userId) throws EmptyResultDataAccessException{
		return jdbcTemplate.queryForObject("SELECT orderId FROM orderList "
				+ "WHERE orderType = ? AND currencyBuy = ? AND currencySell = ? AND "
				+ "size = ? AND submittedTime = ? AND userId = ? "
				+ "ORDER BY submittedTime DESC LIMIT 1", 
				new Object[]{orderType, currencyBuyInput, currencySellInput, size, submittedTime, userId}, 
				new OrderRowMapper());
	}
}

class OrderRowMapper implements RowMapper<Order>{

	@Override
	public Order mapRow(ResultSet rs, int i) throws SQLException {
		Order order=new Order();
		order.setOrderId(rs.getInt("orderId"));
		System.out.println("order id received in order row mapper is: "+order.getOrderId());
		return order;
	}
}
	
