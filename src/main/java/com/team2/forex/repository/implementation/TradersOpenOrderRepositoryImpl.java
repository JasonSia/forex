/*package com.team2.forex.repository.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.team2.forex.entity.Currency;
import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;
import com.team2.forex.repository.TradersOpenOrderRepository;

@Repository
public class TradersOpenOrderRepositoryImpl implements TradersOpenOrderRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(readOnly=true)
	public List<Order> getOpenOrders() throws EmptyResultDataAccessException {
		return jdbcTemplate.query("select * from orderList where orderType='limit'and status=unfilled or status=partiallyfilled",
				new RowMapper <Order>() {
					@Override public Order mapRow(ResultSet rs, int rowNum) throws SQLException {	
						Order openOrder = new Order(
								rs.getInt("orderId"),
								rs.getString("orderType"),
								rs.getString("currencyBuy"),
								rs.getString("currencySell"),
								rs.getDouble("size"),
								rs.getDouble("preferredPrice"),
								rs.getDouble("executedPrice"),
								rs.getString("status"),
								rs.getTimestamp("goodTillDate"),
								rs.getTimestamp("submittedTime"),
								rs.getTimestamp("executedTime"),
								rs.getString("userId"));
						return openOrder;
					}					
				}
		
		
		);//end query				
	}//close getopenOrdersMethod
	
	
}//end class
*/