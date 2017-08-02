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
	public void checkDataTest(){
		String [] csv = {"USD/CAD","1.3031","400","2015-09-25_23:04:59.078"};
		boolean actualCheckData = DataFormatCheckUtil.checkData(csv);
		assertEquals("the expected value should be true",true,actualCheckData);
		
	}
}
