package com.team2.forex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
}
