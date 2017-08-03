package com.team2.forex.repository.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		return jdbcTemplate.query("select * from orderList where orderType='limit'and (status='NOTFILLED' or status='PARTIALLYFILLED')",
				new RowMapper <Order>() {
					@Override public Order mapRow(ResultSet rs, int rowNum) throws SQLException {	
						Order openOrder = new Order(
								rs.getInt("orderId"),
								rs.getString("orderType"),
								Currency.valueOf(rs.getString("currencyBuy")),
								Currency.valueOf(rs.getString("currencySell")),
								rs.getInt("size"),
								rs.getDouble("preferredPrice"),
								rs.getDouble("executedPrice"),
								Status.valueOf(rs.getString("status")),
								rs.getTimestamp("goodTillDate"),
								rs.getTimestamp("submittedTime"),
								rs.getTimestamp("executedTime"),
								rs.getString("userId"),
								rs.getString("orderNumber"));
						return openOrder;
					}							
				} //end rowmapper
			);//end query		
	
	}//close getopenOrdersMethod
}//end class
