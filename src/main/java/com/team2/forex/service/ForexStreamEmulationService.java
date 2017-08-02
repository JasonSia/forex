package com.team2.forex.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.team2.forex.entity.Currency;
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.repository.HistoricalTradeDataRepository;

@Service
public class ForexStreamEmulationService {
	@Autowired
	private HistoricalTradeDataRepository historicalRepo;
	
	public void processStreamJson(String streamJson){
		
	}
	
	public String generateStreamJson() throws JSONException{
		Currency[] currencyArray = Currency.values();
		Random random = new Random();
		JSONArray jsonArray = new JSONArray();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		
		//generate list of trade data
		for(int i = 0; i < currencyArray.length; i++){
			for(int j = 0; j < currencyArray.length; j++){
				//skip if i = j
				if(i == j) continue;
				
				//get currency pair
				Currency buy = currencyArray[i];
				Currency sell = currencyArray[j];
				
				double price = 0;
				try{
					//get existing trade data and calculate new price
					HistoricalTradeData historicalTradeData = historicalRepo.getLatestHistoricalTradeData(buy, sell);
					
					//if 0, minus; if 1, plus
					int plusMinus = random.nextInt(2);
					if(plusMinus == 0){
						price = historicalTradeData.getPrice() - random.nextInt(101);
					}else{
						price = historicalTradeData.getPrice() + random.nextInt(101);
					}
					
				}catch(EmptyResultDataAccessException ex){
					//if no past price, generate new price
					price = (random.nextInt(500) + 1.0) / 100.0;
				}
				
				int lotSize = random.nextInt(1000) + 1;
				
				JSONObject obj = new JSONObject();
				obj.put("buy", buy.toString());
				obj.put("sell", sell.toString());
				obj.put("price", price);
				obj.put("lotSize", lotSize);
				obj.put("timestamp", timestamp.toString());
				
				jsonArray.put(obj);
			}
		}
		
		return jsonArray.toString();
	}
	
	public String generateIncorrectStreamJson(){
		return null;
	}
}
