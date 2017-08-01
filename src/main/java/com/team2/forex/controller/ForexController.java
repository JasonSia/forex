package com.team2.forex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team2.forex.service.*;
import com.team2.forex.entity.Order;

@RestController
public class ForexController {

	@Autowired
	private MarketOrderService mos;
	
	@Autowired
	private ForexStreamEmulationService emulationService;
	
	@RequestMapping(value="/placeMarketOrder", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
	public String welcome(Order userOrder){
		return mos.placeMarketOrder(userOrder);
	}
	
	/*@RequestMapping(value="/importDatafile", method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public User createUser(@RequestBody User user){
		repo.create(user);
		return user;
	}*/

	@Scheduled(fixedRate=60000)
	@RequestMapping(value="/runStreamEmulation")
	public void runStreamEmulation(){
		String streamJson = ForexStreamEmulationService.generateStreamJson();
	}
	
}
