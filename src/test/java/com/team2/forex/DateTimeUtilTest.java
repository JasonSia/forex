package com.team2.forex;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		Date date = new Date();
		SimpleDateFormat sdf= new SimpleDateFormat ("yyyy-MM-dd_HH:mm:ss.SSS");
		
		String result = DateTimeUtil.timestampToString(new Timestamp(date.getTime()));
 		assertEquals("expected string should be ", sdf.format(date),result);	
	}
	
	@Test
	public void stringToTimeStamp() throws ParseException{
		Date date = new Date();
		SimpleDateFormat sdf= new SimpleDateFormat ("yyyy-MM-dd_HH:mm:ss.SSS");
		String stringTs = "2016-06-23_22:04:53.078";
		
		
		Timestamp ts = DateTimeUtil.stringToTimestamp(stringTs);
		
		Timestamp expectedTimestamp = new Timestamp(sdf.parse(stringTs).getTime());	
		assertEquals("expected timestamp is wrong",expectedTimestamp, ts);
		
		
	}
}
