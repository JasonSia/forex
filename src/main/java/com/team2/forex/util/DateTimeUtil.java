package com.team2.forex.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	public static String timestampToString (Timestamp ts){
		SimpleDateFormat sdf= new SimpleDateFormat ("yyyy-MM-dd_HH:mm:ss.SSS");
		Date date = new Date(ts.getTime());
		return sdf.format(date);
			
	}
	
	public static Timestamp stringToTimestamp (String timeString) throws ParseException{
		Timestamp ts;	
		SimpleDateFormat sdf= new SimpleDateFormat ("yyyy-MM-dd_HH:mm:ss.SSS");
		ts= new Timestamp(sdf.parse(timeString).getTime());		
		return ts;
	}
}
