package com.team2.forex.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.team2.forex.util.DateTimeUtil;

@Service
public class ForexStreamEmulationService {
	@Autowired
	private HistoricalTradeDataRepository historicalRepo;
	
	public List<HistoricalTradeData> parseStreamJson(String streamJson) throws JSONException, ParseException{
		List<HistoricalTradeData> dataList = new ArrayList<HistoricalTradeData>();
		JSONArray jsonArray = new JSONArray(streamJson);
		
		//add into list
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject obj = jsonArray.getJSONObject(i);
			
			//to add validation
			String currencyPair = obj.getString("currencyPair");
			double price = obj.getDouble("price");
			int lotSize = obj.getInt("lotSize");
			Timestamp ts = DateTimeUtil.stringToTimestamp(obj.getString("timestamp"));
			
			Currency sell = Currency.valueOf(currencyPair.substring(0,3));
			Currency buy = Currency.valueOf(currencyPair.substring(4,7));
			
			//create HistoricalTradeData and add in list
			dataList.add(new HistoricalTradeData(buy, sell, price, lotSize, ts));
		}
		return dataList;
	}
	
	public void saveHistoricalTradeData(List<HistoricalTradeData> historicalTradeDataList){
		for(HistoricalTradeData historicalTradeData : historicalTradeDataList){
			historicalRepo.createHistoricalTradeData(historicalTradeData);
		}
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
				obj.put("currencyPair", buy.name() + "/" + sell.name());
				obj.put("price", price);
				obj.put("lotSize", lotSize);
				obj.put("timestamp", DateTimeUtil.timestampToString(timestamp));
				
				jsonArray.put(obj);
			}
		}
		
		return jsonArray.toString();
	}
	
	public String generateIncorrectStreamJson() throws JSONException{
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
				obj.put("currencyPair", buy.name() + "/" + sell.name());
				
				if(j == 1){
					obj.put("price", "abc");
				}else{
					obj.put("price", price);
				}

				obj.put("lotSize", lotSize);
				obj.put("timestamp", DateTimeUtil.timestampToString(timestamp));
				
				jsonArray.put(obj);
			}
		}
		
		return jsonArray.toString();
	}
}
