package com.team2.forex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;
import com.team2.forex.repository.LimitOrderRepository;
import com.team2.forex.service.ForexMatchingService;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, classes = {ForexApplication.class})
public class ForexMatchingTest {
	@Autowired
	private ForexMatchingService matchingService;
	
	@Autowired
	private LimitOrderRepository limitOrderRepository;
	
	@Value("${server.port}")
	private int serverPort;
	
	@Before
	public void init(){
		 RestAssured.port = serverPort;
	}
	
	@Test
	public void testMatchingAlgorithm(){
		Timestamp ts = new Timestamp(new Date().getTime());
		Order[] order = limitOrderRepository.matchLimitOrder();
		Order buyOrder = order[0];
		Order sellOrder = order[1];
		
		//check currencies
		assertEquals("Buy currency of A should be equal to sell currency of B", buyOrder.getCurrencyBuy(), sellOrder.getCurrencySell());
		assertEquals("Sell currency of A should be equal to Buy currency of B", buyOrder.getCurrencySell(), sellOrder.getCurrencyBuy());
		
		//check lot size
		assertThat("Lot size of A should be greater or equal to B", buyOrder.getSize(), greaterThan(sellOrder.getSize()));
		
		//check status
		assertTrue("Status of A should be unfilled or partially filled", 
				(buyOrder.getStatus() == Status.NOTFILLED || buyOrder.getStatus() == Status.PARTIALLYFILLED));
		assertTrue("Status of B should be unfilled or partially filled", 
				(sellOrder.getStatus() == Status.NOTFILLED || sellOrder.getStatus() == Status.PARTIALLYFILLED));
		
		//check if limit order
		assertEquals("A should be a limit order", "limit", buyOrder.getOrderType());
		assertEquals("B should be a limit order", "limit", sellOrder.getOrderType());
		
		//check preferred price of A > B
		assertThat("Price of A should be greater or equal to B", buyOrder.getPreferredPrice(), greaterThan(sellOrder.getPreferredPrice()));
		
		//check goodtilldate exceeds current date
		assertThat("Goodtilldate of A should be later than current date", buyOrder.getGoodTillDate(), greaterThan(ts));
		assertThat("Goodtilldate of B should be later than current date", sellOrder.getGoodTillDate(), greaterThan(ts));
	}
	
	@Test
	public void testCleanUpLimitOrder(){
		matchingService.cleanUpLimitOrder();
	}
	
	@Test
	public void testMatchingService(){
		
	}
}
