package com.team2.forex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team2.forex.service.*;
import com.team2.forex.repository.*;

@RestController
public class ForexController {

	@Autowired
	MarketOrderService mos;
	
	@RequestMapping("/placeMarketOrder")
	public String welcome(){
		//return fs.;
		return null;
	}
	
	/*@RequestMapping(value="/importDatafile", method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public User createUser(@RequestBody User user){
		repo.create(user);
		return user;
	}*/

	
}
