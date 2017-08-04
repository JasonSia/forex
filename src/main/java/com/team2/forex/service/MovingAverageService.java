package com.team2.forex.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.forex.entity.Currency;
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.repository.HistoricalTradeDataRepository;

@Service
public class MovingAverageService {
	
	@Autowired
	private HistoricalTradeDataRepository histRepo;
	
	public String calc() {
		String result = "Moving Average for the currency pair is as follows: "+"\n";
		List<HistoricalTradeData> histData;
		long DAY_IN_MS = 1000 * 60 * 60 * 24;
		for(Currency c_sell: Currency.values()){
			for(Currency c_buy: Currency.values()){
				if(c_buy != c_sell){
					histData = histRepo.getHistoricalTradeData(c_buy, c_sell);
					double avg = 0.0;
					for(HistoricalTradeData data: histData){
						Date ideal_dt = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
						if(data.getTimestamp().getTime() >= ideal_dt.getTime()){
							avg+=data.getPrice();
						}
					}
					avg/=10;
					result += "Buy: " + c_buy.name() + " Sell: " + c_sell.name() + " ----- avg: "+avg+" \n ";
				}
			}
		}
		
		return result;
	}
	
}
