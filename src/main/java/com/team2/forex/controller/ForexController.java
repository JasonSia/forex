package com.team2.forex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team2.forex.service.*;
import com.team2.forex.entity.Order;
import com.team2.forex.repository.*;

@RestController
public class ForexController {

	@Autowired
	MarketOrderService mos;
	
	//@Autowired
	//ForexDataReaderService fdrs;

	@RequestMapping(value="/placeMarketOrder", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
	public String welcome(Order userOrder){
		return mos.placeMarketOrder(userOrder);
	}
	
	/*@RequestMapping(value="/importDatafile", method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public String importFile(@RequestBody String fileName){
		fdrs.parseCSV(fileName);
		return "done";
	}*/

	
}
