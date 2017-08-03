package com.team2.forex.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
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
import com.team2.forex.util.OrderUtil;
import com.team2.forex.entity.Currency;
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;

@RestController
public class ForexController {

	@Autowired
	private MarketOrderService mos;
	
	@Autowired
	private LimitOrderService los;
	
	@Autowired
	private ForexStreamEmulationService emulationService;
	
	@Autowired
	private ForexDataReaderService fdrs;
	
	@Autowired
	private ForexMatchingService matchingService;
	
	private static final Logger LOGGER = Logger.getLogger( ForexController.class.getName() );

	
	@RequestMapping(value="/placeMarketOrder", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String createMarketOrder(@RequestBody Order userOrder)throws NoSuchAlgorithmException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userid = auth.getName();
		if(!(userOrder.getOrderType().equalsIgnoreCase("market"))){
			return "OrderType should only be Market";
		}
		else if(userOrder.getCurrencyBuy()==null)
		{   return "ERROR: Buy Currency not supported.";	
		}
		else if(userOrder.getCurrencySell()==null)
		{   return "ERROR: Sell Currency not supported.";	
		}
		else
		{
		userOrder.setStatus(Status.NOTFILLED);
		userOrder.setSubmittedTime(new Timestamp(System.currentTimeMillis()));
		userOrder.setUserId(userid);
		userOrder.setOrderNumber(OrderUtil.generateOrderNumber(userid));
		//userOrder.setCurrencyBuy(Currency.valueOf(userOrder.getCurrencyBuyInput()));
		//userOrder.setCurrencySell(Currency.valueOf(userOrder.getCurrencySellInput()));
		Order completedOrder=mos.placeMarketOrder(userOrder);
		if(completedOrder!=null){
			String toPrint="Your order's unique ID is: "+completedOrder.getOrderId()+"\n"+
						   "Your order's order number is: "+completedOrder.getOrderNumber()+"\n"+
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
	public String createLimitOrder(@RequestBody Order userOrder)throws NoSuchAlgorithmException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userid = auth.getName();
		if(!(userOrder.getOrderType().equalsIgnoreCase("limit"))){
			return "ERROR: OrderType should only be Limit";
		}
		else if(userOrder.getCurrencyBuy()==null)
		{   return "ERROR: Buy Currency not supported.";	
		}
		else if(userOrder.getCurrencySell()==null)
		{   return "ERROR: Sell Currency not supported.";	
		}
		else
		{
		userOrder.setStatus(Status.NOTFILLED);
		userOrder.setSubmittedTime(new Timestamp(System.currentTimeMillis()));
		//userOrder.setCurrencyBuy(Currency.valueOf(userOrder.getCurrencyBuyInput()));
		//userOrder.setCurrencySell(Currency.valueOf(userOrder.getCurrencySellInput()));
		userOrder.setUserId(userid);
		userOrder.setOrderNumber(OrderUtil.generateOrderNumber(userid));
		//userOrder.setPreferredPrice(userid);
		Order completedOrder=los.placeLimitOrder(userOrder);
		String toPrint="Successful: Your limit order is placed, Your order's unique ID is: "+completedOrder.getOrderId()+"\n"+
				       "Your order's order number is: "+completedOrder.getOrderNumber()+"\n";
		return toPrint;
		}
	}
	
	@RequestMapping(value="/cancelLimitOrder", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String cancelLimitOrder(@RequestBody String orderJson){
		JSONObject obj;
		try {
			obj = new JSONObject(orderJson);
			String orderNumber = obj.getString("orderNumber");
			System.out.println("orderId captured from user: "+orderNumber);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String userid = auth.getName();
			String cancelResult=los.cancelLimitOrder(orderNumber, userid);
			return cancelResult;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	@RequestMapping(value="/importDatafile", method=RequestMethod.GET)
	public String importFile(){
		try {
			fdrs.parseCSV();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "File fetched to the Database";
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
		matchingService.processLimitOrderMatching();
		return "helloworld";
	}
	
	@RequestMapping("/runLimitOrderMatching")
	public void runLimitOrderMatching(){
		matchingService.processLimitOrderMatching();
		matchingService.cleanUpLimitOrder();
	}
	
}
