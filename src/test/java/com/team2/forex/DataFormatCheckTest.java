package com.team2.forex;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.util.DataFormatCheckUtil;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ForexApplication.class})
public class DataFormatCheckTest {
	
	//check test data
	@Test
	public void checkDataTest(){ //overall check
		String [] csv = {"USD/CAD","1.3031","400","2015-09-25_23:04:59.078"};
		boolean actualCheckData = DataFormatCheckUtil.checkData(csv);
		System.out.println(actualCheckData);
		assertEquals("the expected value should be false",false,actualCheckData);
		
	}
	
	@Test
	public void buySellFormatTest(){ //currencyCheck
		String [] csv = {"USD:CAD","1.3031","400","2015-09-25_23:04:59.078"};
		boolean actualCheckData = DataFormatCheckUtil.checkData(csv);
		System.out.println(actualCheckData);
		assertEquals("the expected value should be true",true,actualCheckData);
		
	}
	
	@Test
	public void missingCurrencyTest(){ //missing currency
		String [] csv = {"USR/CAD","1.3031","400","2015-09-25_23:04:59.078"};
		boolean actualCheckData = DataFormatCheckUtil.checkData(csv);
		System.out.println(actualCheckData);
		assertEquals("the expected value should be true",true,actualCheckData);
		
	}
	
	@Test
	public void timeStampTest(){ //missing currency
		String [] csv = {"SGD/CAD","1.3031","400","S_23:04:59.078"};
		boolean actualCheckData = DataFormatCheckUtil.checkData(csv);
		System.out.println(actualCheckData);
		assertEquals("the expected value should be true",true,actualCheckData);
		
	}
	
	@Test
	public void lotSizeTest(){ //lot size test
		String [] csv = {"SGD/CAD","1.3031","-300.10","S_23:04:59.078"};
		boolean actualCheckData = DataFormatCheckUtil.checkData(csv);
		System.out.println(actualCheckData);
		assertEquals("the expected value should be true",true,actualCheckData);
		
	}
	
	@Test
	public void priceTest(){ //wrong pricing
		String [] csv = {"SGD/CAD","abc","400","S_23:04:59.078"};
		boolean actualCheckData = DataFormatCheckUtil.checkData(csv);
		System.out.println(actualCheckData);
		assertEquals("the expected value should be true",true,actualCheckData);
		
	}
	
	
	
	
}
