package com.team2.forex.repository.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.team2.forex.entity.Currency;
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.repository.HistoricalTradeDataRepository;

@Repository
public class HistoricalTradeDataRepositoryImpl implements HistoricalTradeDataRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(readOnly=true)
	public HistoricalTradeData getLatestHistoricalTradeData(Currency buy, Currency sell) throws EmptyResultDataAccessException{
		return jdbcTemplate.queryForObject("SELECT historicalId, currencyBuy, currencySell, "
				+ "lastPrice, lotSize, transactionTime FROM historical "
				+ "WHERE currencyBuy = ? AND currencySell = ? "
				+ "ORDER BY transactionTime DESC LIMIT 1", 
				new Object[]{buy.toString(), sell.toString()}, 
				new HistoricalTradeDataRowMapper());
	}

	@Override
	@Transactional
	public HistoricalTradeData createHistoricalTradeData(HistoricalTradeData historicalTradeData) {
		final String sql = "insert into historical(currencyBuy, currencySell, lastPrice, lotSize, transactionTime) values (?,?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator(){
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
				
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, historicalTradeData.getBuy().name());
				ps.setString(2, historicalTradeData.getSell().name());
				ps.setDouble(3, historicalTradeData.getPrice());
				ps.setInt(4, historicalTradeData.getLotSize());
				ps.setTimestamp(5, historicalTradeData.getTimestamp());
				return ps;
			}
		}, holder);
		
		int newHistoricalId = holder.getKey().intValue();
		historicalTradeData.setHistoricalTradeDataId(newHistoricalId);
		return historicalTradeData;
	}

}

class HistoricalTradeDataRowMapper implements RowMapper<HistoricalTradeData>{

	@Override
	public HistoricalTradeData mapRow(ResultSet rs, int i) throws SQLException {
		return new HistoricalTradeData(rs.getInt("historicalId"),
				Currency.valueOf(rs.getString("currencyBuy")),
				Currency.valueOf(rs.getString("currencySell")),
				rs.getDouble("lastPrice"),
				rs.getInt("lotSize"),
				rs.getTimestamp("transactionTime"));
	}
	
}
