package com.team2.forex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.team2.forex.entity.Order;
import com.team2.forex.repository.HistoricalTradeDataRepository;
import com.team2.forex.repository.MarketOrderRepository;

@Component
public class MarketOrderService {

	@Autowired
	MarketOrderRepository marketOrderRp;
	
	@Autowired
	HistoricalTradeDataRepository historicalTradeDataRp;
	
	public Order placeMarketOrder(Order mktOrder){
		System.out.println("historical lot size is: "+historicalTradeDataRp.getLatestHistoricalTradeData(mktOrder.getCurrencyBuy(), mktOrder.getCurrencySell()).getLotSize());
		System.out.println("Entered lot size is: "+mktOrder.getSize());
		if(historicalTradeDataRp.getLatestHistoricalTradeData(mktOrder.getCurrencyBuy(), mktOrder.getCurrencySell()).getLotSize()>mktOrder.getSize()){
			return marketOrderRp.PlaceAndExecuteMarketOrder(mktOrder, historicalTradeDataRp.getLatestHistoricalTradeData(mktOrder.getCurrencyBuy(), mktOrder.getCurrencySell()).getPrice());
		}
		else
			return null;
	}
	
}
