package com.team2.forex;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.entity.Order;
import com.team2.forex.repository.TradersOpenOrderRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ForexApplication.class})
public class OpenOrderTest {
	
	
	@Autowired
	private TradersOpenOrderRepository openOrder; 
	
	@Test
	public void testCountOpenOrder(){
		List<Order> openOrders = openOrder.getOpenOrders();
		assertEquals("There should only be 4 open orders", 4, openOrders.size());
		
	}
	
}
