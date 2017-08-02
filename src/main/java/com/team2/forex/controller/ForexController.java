package com.team2.forex.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team2.forex.service.*;
import com.team2.forex.entity.Currency;
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;

@RestController
public class ForexController {

	@Autowired
	private MarketOrderService mos;
	
	@Autowired
	private ForexStreamEmulationService emulationService;
	
	@Autowired
	private ForexDataReaderService fdrs;
	
	private static final Logger LOGGER = Logger.getLogger( ForexController.class.getName() );

	
	@RequestMapping(value="/placeMarketOrder", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String createMarketOrder(@RequestBody Order userOrder){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userid = auth.getName();
		int flagBuy=0;
		int flagSell=0;
		for(Currency c: Currency.values()){
			if(userOrder.getCurrencyBuyInput().equalsIgnoreCase(c.name()))
				flagBuy=1;
			if(userOrder.getCurrencySellInput().equalsIgnoreCase(c.name()))
				flagSell=1;
		}
		if(!(userOrder.getOrderType().equalsIgnoreCase("market") || userOrder.getOrderType().equalsIgnoreCase("limit"))){
			return "OrderType should only be Market or Limit";
		}
		else if(flagBuy!=1)
		{   return "ERROR: Buy Currency not supported.";	
		}
		else if(flagSell!=1)
		{   return "ERROR: Sell Currency not supported.";	
		}
		else
		{
		userOrder.setStatus(Status.NOTFILLED);
		userOrder.setSubmittedTime(new Timestamp(System.currentTimeMillis()));
		userOrder.setCurrencyBuy(Currency.valueOf(userOrder.getCurrencyBuyInput()));
		userOrder.setCurrencySell(Currency.valueOf(userOrder.getCurrencySellInput()));
		Order completedOrder=mos.placeMarketOrder(userOrder);
		if(completedOrder!=null){
			String toPrint="Your order's unique ID is: "+completedOrder.getOrderId()+"\n"+
						   "The executed price is: "+completedOrder.getExecutedPrice()+"\n"+
						   "The size is: "+completedOrder.getSize()+"\n"+
						   "Total payment is: "+(completedOrder.getExecutedPrice()*completedOrder.getSize())+"\n"+
						   "The submitted time is: "+completedOrder.getSubmittedTime()+"\n"+
						   "The executed time is: "+completedOrder.getExecutedTime();
		   return toPrint;
		}
		else
		   return "Your input size is more than the lot size currently available, please try again.";
	}
		}
	
	
	@RequestMapping(value="/placeLimitOrder", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String createLimitOrder(@RequestBody Order userOrder){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userid = auth.getName();
		int flagBuy=0;
		int flagSell=0;
		for(Currency c: Currency.values()){
			if(userOrder.getCurrencyBuyInput().equalsIgnoreCase(c.name()))
				flagBuy=1;
			if(userOrder.getCurrencySellInput().equalsIgnoreCase(c.name()))
				flagSell=1;
		}
		if(!(userOrder.getOrderType().equals("BUY") || userOrder.getOrderType().equals("SELL"))){
			return "ERROR: OrderType should only be BUY or SELL";
		}
		else if(flagBuy!=1)
		{   return "ERROR: Buy Currency not supported.";	
		}
		else if(flagSell!=1)
		{   return "ERROR: Sell Currency not supported.";	
		}
		else
		{
		userOrder.setStatus(Status.NOTFILLED);
		userOrder.setSubmittedTime(new Timestamp(System.currentTimeMillis()));
		userOrder.setCurrencyBuy(Currency.valueOf(userOrder.getCurrencyBuyInput()));
		userOrder.setCurrencySell(Currency.valueOf(userOrder.getCurrencySellInput()));
		userOrder.setUserId(userid);
		//userOrder.setPreferredPrice(userid);
		return "SUCCESSFUL: Your limit order is placed and the unique ID is: "+mos.placeMarketOrder(userOrder);
		}
	}
	
	@RequestMapping(value="/importDatafile", method=RequestMethod.GET)
	public String importFile(){
		try {
			fdrs.parseCSV();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "done";
	}

	@Scheduled(fixedDelayString="${com.team2.forex.emulation.refreshrate}")
	@RequestMapping(value="/runStreamEmulation")
	public void runStreamEmulation(){
		try {
			//generate stream json based on chance
			//if 0, incorrect json; else, correct json
			String streamJson;
			Random random = new Random();
			int chance = random.nextInt(4);
			
			if(chance == 0){
				streamJson = emulationService.generateIncorrectStreamJson();
			}else{
				streamJson = emulationService.generateStreamJson();
			}
			
			//parse and process json
			List<HistoricalTradeData> dataList = emulationService.parseStreamJson(streamJson);
			emulationService.saveHistoricalTradeData(dataList);
		} catch (JSONException e) {
			//e.printStackTrace();
			LOGGER.info("Malformed Stream Message: " + e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/helloworld")
	public String helloWorld(){
		return "helloworld";
	}
	
}
