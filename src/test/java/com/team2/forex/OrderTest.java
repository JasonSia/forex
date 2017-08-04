package com.team2.forex;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.entity.Order;
import com.team2.forex.repository.TradersOrderRepository;
import com.team2.forex.util.DateTimeUtil;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ForexApplication.class})
public class OrderTest {
	
	
	@Autowired
	private TradersOrderRepository orderRp; 
	
	
	@Test
	public void testCountOpenOrder(){
		List<Order> openOrders = orderRp.getOpenOrders();
		assertEquals("There should only be 5 open orders", 5, openOrders.size());
		
	}
	
	@Test
	public void testClosedOpenOrder() throws ParseException{
		Timestamp tsStart = DateTimeUtil.stringToTimestamp("2017-08-10_23:04:15.000");
		Timestamp tsEnd = DateTimeUtil.stringToTimestamp("2017-08-16_23:04:15.000");
		List<Order> closedOrders = orderRp.getClosedOrders(tsStart,tsEnd);
		assertEquals("There should only be 3 closed orders", 3, closedOrders.size());
	}

}
	