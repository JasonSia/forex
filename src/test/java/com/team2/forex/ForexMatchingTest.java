package com.team2.forex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.entity.Currency;
import com.team2.forex.entity.Order;
import com.team2.forex.entity.Status;
import com.team2.forex.repository.LimitOrderRepository;
import com.team2.forex.service.ForexMatchingService;
import com.team2.forex.util.DateTimeUtil;
import com.team2.forex.util.OrderUtil;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, classes = {ForexApplication.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	public void test1MatchingAlgorithm() throws ParseException, NoSuchAlgorithmException{
		Order newOrder = new Order();
		newOrder.setOrderType("LIMIT");
		newOrder.setCurrencyBuy(Currency.SGD.name());
		newOrder.setCurrencySell(Currency.AUD.name());
		newOrder.setPreferredPrice(1.92);
		newOrder.setGoodTillDate(DateTimeUtil.stringToTimestamp("2017-08-15_00:53:49.079"));
		newOrder.setSize(50);
		newOrder.setStatus(Status.NOTFILLED);
		newOrder.setSubmittedTime(new Timestamp(System.currentTimeMillis()));
		newOrder.setUserId("client");
		newOrder.setOrderNumber(OrderUtil.generateOrderNumber("client"));
		limitOrderRepository.PlaceLimitOrder(newOrder);
		
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
		assertEquals("A should be a limit order", "LIMIT", buyOrder.getOrderType());
		assertEquals("B should be a limit order", "LIMIT", sellOrder.getOrderType());
		
		//check preferred price of A > B
		assertThat("Price of A should be greater or equal to B", buyOrder.getPreferredPrice(), greaterThan(sellOrder.getPreferredPrice()));
		
		//check goodtilldate exceeds current date
		assertThat("Goodtilldate of A should be later than current date", buyOrder.getGoodTillDate(), greaterThan(ts));
		assertThat("Goodtilldate of B should be later than current date", sellOrder.getGoodTillDate(), greaterThan(ts));
	}
	
	@Test
	public void test2CleanUpLimitOrder() throws ParseException, NoSuchAlgorithmException{
		//create old order
		Order newOrder = new Order();
		newOrder.setOrderType("LIMIT");
		newOrder.setCurrencyBuy(Currency.SGD.name());
		newOrder.setCurrencySell(Currency.AUD.name());
		newOrder.setPreferredPrice(1.92);
		newOrder.setGoodTillDate(DateTimeUtil.stringToTimestamp("2013-08-15_00:53:49.079"));
		newOrder.setSize(50);
		newOrder.setStatus(Status.NOTFILLED);
		newOrder.setSubmittedTime(new Timestamp(System.currentTimeMillis()));
		newOrder.setUserId("client");
		newOrder.setOrderNumber(OrderUtil.generateOrderNumber("client"));
		limitOrderRepository.PlaceLimitOrder(newOrder);
				
		//clean up old orders
		Timestamp ts = new Timestamp(new Date().getTime());
		matchingService.cleanUpLimitOrder();
		List<Order> limitOrderList = limitOrderRepository.getAllOpenLimitOrder();
		
		//check that all limit orders are later than current date after clean up
		for(Order order: limitOrderList){
			assertThat("Goodtilldate should be later than current date", order.getGoodTillDate(), greaterThan(ts));
		}
	}
	
	@Test
	public void test3MatchingService() throws ParseException, NoSuchAlgorithmException{
		//place matching orders
		Order newOrder = new Order();
		newOrder.setOrderType("LIMIT");
		newOrder.setCurrencyBuy(Currency.SGD.name());
		newOrder.setCurrencySell(Currency.AUD.name());
		newOrder.setPreferredPrice(1.92);
		newOrder.setGoodTillDate(DateTimeUtil.stringToTimestamp("2017-08-15_00:53:49.079"));
		newOrder.setSize(50);
		newOrder.setStatus(Status.NOTFILLED);
		newOrder.setSubmittedTime(new Timestamp(System.currentTimeMillis()));
		newOrder.setUserId("client");
		newOrder.setOrderNumber(OrderUtil.generateOrderNumber("client"));
		limitOrderRepository.PlaceLimitOrder(newOrder);
		
		newOrder.setCurrencyBuy(Currency.AUD.name());
		newOrder.setCurrencySell(Currency.SGD.name());
		limitOrderRepository.PlaceLimitOrder(newOrder);
		
		//test rest service
		given()
		.auth().basic("client", "client")
		.when().get("/runLimitOrderMatching")
		.then().statusCode(HttpStatus.SC_OK);
		
		//run matching algorithm to check if there are still matching order
		try{
			limitOrderRepository.matchLimitOrder();
			Assert.fail("There should not be any matching limit order after running matching service");
		}catch(EmptyResultDataAccessException e){
			//continue to run
		}
		
		//check that all limit orders are later than current date after clean up
		Timestamp ts = new Timestamp(new Date().getTime());
		List<Order> limitOrderList = limitOrderRepository.getAllOpenLimitOrder();
		for(Order testOrder: limitOrderList){
			assertThat("Goodtilldate should be later than current date", testOrder.getGoodTillDate(), greaterThan(ts));
		}
	}
}
