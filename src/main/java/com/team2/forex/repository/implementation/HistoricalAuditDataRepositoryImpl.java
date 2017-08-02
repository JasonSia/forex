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
import com.team2.forex.entity.HistoricalAudit;
import com.team2.forex.repository.HistoricalAuditDataRepository;

@Repository
public class HistoricalAuditDataRepositoryImpl implements HistoricalAuditDataRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public HistoricalAudit createHistoricalAuditData(HistoricalAudit historicalAuditData) {
		final String sql = "insert into historicalAudit(historicalAuditId, fileRowNum, filename, processingTime) values (?,?,?,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator(){
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
				
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, historicalAuditData.getHistoricalAuditId());
				ps.setInt(2, historicalAuditData.getFileRowNum());
				ps.setString(3, historicalAuditData.getFileName());
				ps.setDouble(4, historicalAuditData.getProcessingTime());
				return ps;
			}
		}, holder);
		
		int newHistoricalId = holder.getKey().intValue();
		historicalAuditData.setHistoricalAuditId(newHistoricalId);
		return historicalAuditData;
	}

	@Override
	@Transactional(readOnly=true)
	public HistoricalAudit getHistoricalAuditData(String fileName) throws EmptyResultDataAccessException{
		return jdbcTemplate.queryForObject("SELECT * "
				+ "FROM historicalAudit "
				+ "WHERE filename = ? ", 
				new Object [] {fileName}, 
				new HistoricalAuditDataRowMapper());
	}

}

class HistoricalAuditDataRowMapper implements RowMapper<HistoricalAudit>{

	@Override
	public HistoricalAudit mapRow(ResultSet rs, int i) throws SQLException {
		return new HistoricalAudit(rs.getInt("historicalAuditId"),
				rs.getInt("fileRowNum"),
				rs.getString("fileName"),
				rs.getDouble("processingTime"));
	}
	
}

