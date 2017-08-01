package com.team2.forex.entity;

import java.sql.Timestamp;

public class HistoricalTradeData {
	private int historicalTradeDataId;
	private Currency buy;
	private Currency sell;
	private double price;
	private int lotSize;
	private Timestamp timestamp;
	
	public HistoricalTradeData() {}

	public HistoricalTradeData(Currency buy, Currency sell, double price, int lotSize, Timestamp timestamp) {
		this.buy = buy;
		this.sell = sell;
		this.price = price;
		this.lotSize = lotSize;
		this.timestamp = timestamp;
	}

	public HistoricalTradeData(int historicalTradeDataId, Currency buy, Currency sell, double price, int lotSize,
			Timestamp timestamp) {
		this.historicalTradeDataId = historicalTradeDataId;
		this.buy = buy;
		this.sell = sell;
		this.price = price;
		this.lotSize = lotSize;
		this.timestamp = timestamp;
	}

	public int getHistoricalTradeDataId() {
		return historicalTradeDataId;
	}

	public void setHistoricalTradeDataId(int historicalTradeDataId) {
		this.historicalTradeDataId = historicalTradeDataId;
	}

	public Currency getBuy() {
		return buy;
	}

	public void setBuy(Currency buy) {
		this.buy = buy;
	}

	public Currency getSell() {
		return sell;
	}

	public void setSell(Currency sell) {
		this.sell = sell;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getLotSize() {
		return lotSize;
	}

	public void setLotSize(int lotSize) {
		this.lotSize = lotSize;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
