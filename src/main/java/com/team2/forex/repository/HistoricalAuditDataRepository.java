package com.team2.forex.repository;

import com.team2.forex.entity.HistoricalAudit;

public interface HistoricalAuditDataRepository {
	public HistoricalAudit createHistoricalAuditData(HistoricalAudit historicalTradeData);
	public HistoricalAudit getHistoricalAuditData(String fileName);
}
