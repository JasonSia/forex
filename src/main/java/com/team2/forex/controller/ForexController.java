package com.team2.forex.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.team2.forex.service.*;
import com.team2.forex.repository.*;

public class ForexController {

	@Autowired
	ForexService fs;
	@Autowired
	ForexRepository forexRp;
	
}
