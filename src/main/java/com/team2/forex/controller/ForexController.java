package com.team2.forex.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team2.forex.service.*;
import com.team2.forex.entity.HistoricalTradeData;
import com.team2.forex.entity.Order;

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
	public String welcome(@RequestBody Order userOrder){
		return mos.placeMarketOrder(userOrder);
		  
	}
	
	@RequestMapping(value="/importDatafile", method=RequestMethod.GET)
	public String importFile(){
		fdrs.parseCSV();
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
			emulationService.processStreamList(dataList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
