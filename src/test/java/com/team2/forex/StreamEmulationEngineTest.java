package com.team2.forex;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.entity.Currency;
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.repository.HistoricalTradeDataRepository;
import com.team2.forex.service.ForexStreamEmulationService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ForexApplication.class})
public class StreamEmulationEngineTest {
	
	@Autowired
	private ForexStreamEmulationService emulationService;
	
	@Autowired
	private HistoricalTradeDataRepository historicalRepo;
	
	private final double DELTA = 1e-15;
	
	@Test
	public void testParseStreamJson() throws ParseException{
		try {
			//get stream json
			String streamJson = emulationService.generateStreamJson();
			
			//map json into HistoricalTradeData list
			List<HistoricalTradeData> myObjects = emulationService.parseStreamJson(streamJson);
			
			//check list size
			int numCurrencies = Currency.values().length;
			int numCurrencyPair = numCurrencies * (numCurrencies - 1);
			assertEquals("Number of items in list should be " + numCurrencyPair, numCurrencyPair, myObjects.size());
		} catch (JSONException e) {
			Assert.fail("Could not create stream json");
		}
	}
	
	@Test(expected=JSONException.class)
	public void testParseIncorrectStreamJson() throws JSONException, ParseException{
		//get stream json
		String streamJson = emulationService.generateIncorrectStreamJson();
		
		//map json into HistoricalTradeData list
		List<HistoricalTradeData> myObjects = emulationService.parseStreamJson(streamJson);
	}

	@Test
	public void testStreamEmulation() throws ParseException{
		try {
			//get stream json
			String streamJson = emulationService.generateStreamJson();
			
			//map json into HistoricalTradeData list
			List<HistoricalTradeData> historicalTradeDataList = emulationService.parseStreamJson(streamJson);
			
			//process stream list into db
			emulationService.saveHistoricalTradeData(historicalTradeDataList);
			
			//check if list is correctly inserted into db
			for(HistoricalTradeData historicalTradeData : historicalTradeDataList){
				Currency buy = historicalTradeData.getBuy();
				Currency sell = historicalTradeData.getSell();
				HistoricalTradeData checkHistoricalData = historicalRepo.getLatestHistoricalTradeData(buy, sell);
				
				assertEquals("Lot size should be " + historicalTradeData.getLotSize(), historicalTradeData.getLotSize(), checkHistoricalData.getLotSize());
				assertEquals("Price should be " + historicalTradeData.getPrice(), historicalTradeData.getPrice(), checkHistoricalData.getPrice(), DELTA);
				assertEquals("Timestamp should be " + historicalTradeData.getTimestamp(), historicalTradeData.getTimestamp(), checkHistoricalData.getTimestamp());
			}
		} catch (JSONException e) {
			Assert.fail("Could not create stream json");
		}
	}
	
}
