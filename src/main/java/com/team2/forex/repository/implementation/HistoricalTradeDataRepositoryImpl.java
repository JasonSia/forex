package com.team2.forex.repository.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
		// TODO Auto-generated method stub
		return null;
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
