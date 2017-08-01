package com.team2.forex.repository;

import com.team2.forex.entity.Currency;
import com.team2.forex.entity.HistoricalTradeData;

public interface HistoricalTradeDataRepository {
	public HistoricalTradeData getLatestHistoricalTradeData(Currency buy, Currency sell);
	public HistoricalTradeData createHistoricalTradeData(HistoricalTradeData historicalTradeData);
}
