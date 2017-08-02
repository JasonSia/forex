package com.team2.forex;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.team2.forex.util.DateTimeUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ForexApplication.class})
public class DateTimeUtilTest {
	
	@Test
	public void timeStampToString(){
		//Date date = new Date();
		//String result = DateTimeUtil.timestampToString(new Timestamp(date.getTime()));
 		
	}
	
}
