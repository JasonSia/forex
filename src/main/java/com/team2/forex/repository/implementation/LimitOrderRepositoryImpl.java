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
import com.team2.forex.repository.LimitOrderRepository;

//@Component
@Repository
public class LimitOrderRepositoryImpl implements LimitOrderRepository{
	
    private final static Logger logger = LoggerFactory.getLogger(LimitOrderRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public LimitOrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public int PlaceLimitOrder(Order order) {
      System.out.println("PrePrice" + order.getPreferredPrice());
      int orderId=jdbcTemplate.update("insert into ORDERLIST(ORDERTYPE,CURRENCYBUY,CURRENCYSELL,SIZE,PREFERREDPRICE,EXECUTEDPRICE,STATUS,GOODTILLDATE,SUBMITTEDTIME,EXECUTEDTIME,USERID) "
      		+ "values (?,?,?,?,?,?,?,?,?,?,?)", 
    		  	order.getOrderType(), order.getCurrencyBuy().name(), order.getCurrencySell().name(), order.getSize(), order.getPreferredPrice(), null, order.getStatus().name(), order.getGoodTillDate(), 
    		  	order.getSubmittedTime(), null, order.getUserId());
      return LimitgetOrderId(order.getOrderType(), order.getCurrencyBuy().name(), order.getCurrencySell().name(), order.getSize(), order.getPreferredPrice(), order.getGoodTillDate(), order.getSubmittedTime(), order.getUserId()).getOrderId();
    }
    
    @Override
	@Transactional(readOnly=true)
	public Order LimitgetOrderId(String orderType, String currencyBuyInput, String currencySellInput, int size, double preferredprice, Timestamp goodTillDate, Timestamp submittedTime, String userId) throws EmptyResultDataAccessException{
		return jdbcTemplate.queryForObject("SELECT orderId FROM orderList "
				+ "WHERE orderType = ? AND currencyBuy = ? AND currencySell = ? AND "
				+ "size = ? AND preferredprice = ? AND goodTillDate = ? AND submittedTime = ? AND userId = ? "
				+ "ORDER BY submittedTime DESC LIMIT 1", 
				new Object[]{orderType, currencyBuyInput, currencySellInput, size, preferredprice, goodTillDate, submittedTime, userId}, 
				new LimitOrderRowMapper());
	}
    
    @Override
	@Transactional(readOnly=true)
	public Order checkLimitOrderExists(int orderId) throws EmptyResultDataAccessException{
    	System.out.println("inside checkLimitOrderExists in rep");
    	try{
		return jdbcTemplate.queryForObject("SELECT preferredPrice FROM orderList "
				+ "WHERE orderId = ?", 
				new Object[]{orderId}, 
				new LimitOrderExistsRowMapper());
    	}catch(Exception e){
    		return null;
    	}
	}

	@Override
	public Order getOrderId(String orderType, String currencyBuyInput, String currencySellInput, int size,
			double preferredPrice, Timestamp goodTillDate, Timestamp submittedTime, String userId) {
		// TODO Auto-generated method stub
		return null;
	}
}

class LimitOrderRowMapper implements RowMapper<Order>{

	@Override
	public Order mapRow(ResultSet rs, int i) throws SQLException {
		Order order=new Order();
		order.setOrderId(rs.getInt("orderId"));
		System.out.println("Limit order id received in order row mapper is: "+order.getOrderId());
		return order;
	}
}

class LimitOrderExistsRowMapper implements RowMapper<Order>{

	@Override
	public Order mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("inside checkLimitOrderExistsRowMapper in rep");
		Order order=new Order();
		order.setPreferredPrice(rs.getInt("preferredPrice"));
		System.out.println("Limit order preferredPrice is: "+order.getPreferredPrice());
		return order;
	}
}