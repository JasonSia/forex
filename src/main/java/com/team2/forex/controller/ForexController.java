package com.team2.forex.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team2.forex.service.*;
import com.team2.forex.entity.Currency;
import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;

@RestController
public class ForexController {

	@Autowired
	private MarketOrderService mos;
	
	@Autowired
	private ForexStreamEmulationService emulationService;
	
	@Autowired
	ForexDataReaderService fdrs;

	// produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE}
	@RequestMapping(value="/placeMarketOrder", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String createMarketOrder(@RequestBody Order userOrder){
		int flagBuy=0;
		int flagSell=0;
		for(Currency c: Currency.values()){
			if(userOrder.getCurrencyBuyInput().equalsIgnoreCase(c.name()))
				flagBuy=1;
			if(userOrder.getCurrencySellInput().equalsIgnoreCase(c.name()))
				flagSell=1;
		}
		if(!(userOrder.getOrderType().equals("BUY") || userOrder.getOrderType().equals("SELL"))){
			return "OrderType should only be BUY or SELL";
		}
		else if(flagBuy!=1)
		{   return "Buy Currency not supported.";	
		}
		else if(flagSell!=1)
		{   return "Sell Currency not supported.";	
		}
		else
		{
		userOrder.setStatus(Status.NOTFILLED);
		userOrder.setSubmittedTime(new Timestamp(System.currentTimeMillis()));
		userOrder.setCurrencyBuy(Currency.valueOf(userOrder.getCurrencyBuyInput()));
		userOrder.setCurrencySell(Currency.valueOf(userOrder.getCurrencySellInput()));
		return "Your order's unique ID is: "+mos.placeMarketOrder(userOrder);
		}
	}
	
	@RequestMapping(value="/importDatafile", method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public String importFile(@RequestBody String fileName){
		fdrs.parseCSV(fileName);
		return "done";
	}

	@Scheduled(fixedDelayString="${com.team2.forex.emulation.refreshrate}")
	@RequestMapping(value="/runStreamEmulation")
	public void runStreamEmulation(){
		//System.out.println("Running emulation" + new Date());
		
		try {
			String streamJson = emulationService.generateStreamJson();
			//System.out.println(streamJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
