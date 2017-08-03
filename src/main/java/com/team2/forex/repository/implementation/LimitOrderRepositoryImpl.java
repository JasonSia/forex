package com.team2.forex.repository.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;
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
    public Order PlaceLimitOrder(Order order) {
      System.out.println("PrePrice" + order.getPreferredPrice());
      int orderId=jdbcTemplate.update("insert into ORDERLIST(ORDERTYPE,CURRENCYBUY,CURRENCYSELL,SIZE,PREFERREDPRICE,EXECUTEDPRICE,STATUS,GOODTILLDATE,SUBMITTEDTIME,EXECUTEDTIME,USERID,ORDERNUMBER) "
      		+ "values (?,?,?,?,?,?,?,?,?,?,?,?)", 
    		  	order.getOrderType(), order.getCurrencyBuy().name(), order.getCurrencySell().name(), order.getSize(), order.getPreferredPrice(), null, order.getStatus().name(), order.getGoodTillDate(), 
    		  	order.getSubmittedTime(), null, order.getUserId(), order.getOrderNumber());
      return LimitgetOrder(order.getOrderType(), order.getCurrencyBuy().name(), order.getCurrencySell().name(), order.getSize(), order.getPreferredPrice(), order.getGoodTillDate(), order.getSubmittedTime(), order.getUserId());
    }
    
    @Override
	@Transactional(readOnly=true)
	public Order LimitgetOrder(String orderType, String currencyBuyInput, String currencySellInput, int size, double preferredprice, Timestamp goodTillDate, Timestamp submittedTime, String userId) throws EmptyResultDataAccessException{
		return jdbcTemplate.queryForObject("SELECT orderId, currencyBuy, currencySell, executedPrice, submittedTime, executedTime, size, orderNumber, goodTillDate FROM orderList "
				+ "WHERE orderType = ? AND currencyBuy = ? AND currencySell = ? AND "
				+ "size = ? AND preferredprice = ? AND goodTillDate = ? AND submittedTime = ? AND userId = ? "
				+ "ORDER BY submittedTime DESC LIMIT 1", 
				new Object[]{orderType, currencyBuyInput, currencySellInput, size, preferredprice, goodTillDate, submittedTime, userId}, 
				new LimitOrderRowMapper());
	}
    
    @Override
	@Transactional(readOnly=true)
	public Order checkLimitOrderExists(String orderNumber) throws EmptyResultDataAccessException{
    	System.out.println("inside checkLimitOrderExists in rep, ordernumber=" + orderNumber);
    	try{
		return jdbcTemplate.queryForObject("SELECT orderId, currencyBuy, currencySell, executedPrice, submittedTime, executedTime, size, orderNumber, orderType, preferredPrice, status, goodTillDate, userId FROM orderList "
				+ "WHERE orderNumber = ? ORDER BY EXECUTEDTIME LIMIT 1", 
				new Object[]{orderNumber}, 
				new LimitOrderExistsRowMapper());
    	}catch(Exception e){
    		return null;
    	}
	}
    
    
    @Override
    @Transactional
    public String cancelOrder(int OrderId) {
      System.out.println("start cancel order " + OrderId);
      jdbcTemplate.update("UPDATE ORDERLIST SET status = 'CANCELLED' WHERE OrderId = ?", 
    		  	OrderId);
      return "SUCCESSFUL: The order has been cancelled.";
    }

	@Override
	public Order getOrderId(String orderType, String currencyBuyInput, String currencySellInput, int size,
			double preferredPrice, Timestamp goodTillDate, Timestamp submittedTime, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public Order[] matchLimitOrder() throws EmptyResultDataAccessException{
		String sql = "SELECT * FROM orderList as limitA "
				+ "INNER JOIN orderList AS limitB "
				+ "ON limitA.currencyBuy = limitB.currencySell AND limitA.currencySell = limitB.currencyBuy "
				+ "WHERE limitA.orderType = 'limit' AND limitB.orderType = 'limit' "
				+ "AND (limitA.status = 'NOTFILLED' OR limitA.status = 'PARTIALLYFILLED') "
				+ "AND (limitB.status = 'NOTFILLED' OR limitB.status = 'PARTIALLYFILLED') "
				+ "AND limitA.goodTillDate >= ? AND limitB.goodTillDate >= ? "
				+ "AND limitA.size >= limitB.size "
				+ "AND limitB.preferredPrice < limitA.preferredPrice "
				+ "ORDER BY limitA.submittedTime asc "
				+ "LIMIT 1";
		
		Timestamp ts = new Timestamp(new Date().getTime());
		
		return jdbcTemplate.queryForObject(sql, 
				new Object[]{ts, ts}, 
				new LimitOrderMatchingRowMapper());
	}

	@Override
	@Transactional(readOnly=true)
	public List<Order> getAllOpenLimitOrder() {
		String sql = "SELECT orderId, currencyBuy, currencySell, executedPrice, "
				+ "submittedTime, executedTime, size, orderNumber, goodTillDate "
				+ "FROM orderList WHERE orderType = 'limit' "
				+ "AND (status = 'UNFILLED' OR status = 'PARTIALLYFILLED')";
		return jdbcTemplate.query(sql, new LimitOrderRowMapper());
	}

	@Override
	@Transactional
	public void updateLimitOrder(Order order) {
		jdbcTemplate.update("UPDATE ORDERLIST SET executedPrice = ?, status = ?, executedTime = ?, size = ? WHERE OrderId = ?", 
    		  	new Object[]{order.getExecutedPrice(), order.getStatus().name(), order.getExecutedTime(), order.getSize(), order.getOrderId()});
	}

	@Override
	@Transactional(readOnly=true)
	public List<Order> getAllLimitOrderBeforeCurrentTime() {
		String sql = "SELECT orderId, currencyBuy, currencySell, executedPrice, "
				+ "submittedTime, executedTime, size, orderNumber, goodTillDate "
				+ "FROM orderList WHERE orderType = 'limit' AND goodTillDate < ? "
				+ "AND (status = 'UNFILLED' OR status = 'PARTIALLYFILLED')";
		return jdbcTemplate.query(sql, new Object[]{new Timestamp(new Date().getTime())}, new LimitOrderRowMapper());
	}
}

class LimitOrderRowMapper implements RowMapper<Order>{

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
		order.setGoodTillDate(rs.getTimestamp("goodTillDate"));
		System.out.println("Limit order id received in order row mapper is: "+order.getOrderId());
		return order;
	}
}

class LimitOrderMatchingRowMapper implements RowMapper<Order[]>{

	@Override
	public Order[] mapRow(ResultSet rs, int i) throws SQLException {
		Order orderA = new Order();
		orderA.setOrderId(rs.getInt(1));
		orderA.setOrderType(rs.getString(3));
		orderA.setCurrencyBuy(rs.getString(4));
		orderA.setCurrencySell(rs.getString(5));
		orderA.setSize(rs.getInt(6));
		orderA.setPreferredPrice(rs.getDouble(7));
		orderA.setExecutedPrice(rs.getDouble(8));
		orderA.setStatus(Status.valueOf(rs.getString(9)));
		orderA.setGoodTillDate(rs.getTimestamp(10));
		orderA.setSubmittedTime(rs.getTimestamp(11));
		orderA.setExecutedTime(rs.getTimestamp(12));
		orderA.setUserId(rs.getString(13));
		
		Order orderB = new Order();
		orderB.setOrderId(rs.getInt(14));
		orderB.setOrderType(rs.getString(16));
		orderB.setCurrencyBuy(rs.getString(17));
		orderB.setCurrencySell(rs.getString(18));
		orderB.setSize(rs.getInt(19));
		orderB.setPreferredPrice(rs.getDouble(20));
		orderB.setExecutedPrice(rs.getDouble(21));
		orderB.setStatus(Status.valueOf(rs.getString(22)));
		orderB.setGoodTillDate(rs.getTimestamp(23));
		orderB.setSubmittedTime(rs.getTimestamp(24));
		orderB.setExecutedTime(rs.getTimestamp(25));
		orderB.setUserId(rs.getString(26));
		return new Order[]{orderA, orderB};
	}
}

class LimitOrderExistsRowMapper implements RowMapper<Order>{

	@Override
	public Order mapRow(ResultSet rs, int i) throws SQLException {
		System.out.println("inside checkLimitOrderExistsRowMapper in rep");
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
		System.out.println("Limit order Status is: "+order.getStatus());
		System.out.println("Limit order User is: "+order.getUserId());
		System.out.println("Limit order ID is: "+order.getOrderId());
		return order;
	}
}