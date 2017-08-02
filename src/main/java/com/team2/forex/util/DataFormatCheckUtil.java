package com.team2.forex.util;

import java.text.ParseException;

import com.team2.forex.entity.Currency;

public class DataFormatCheckUtil {
	
	public static boolean checkData(String [] csvData){		
		
		if(!hasCorrectBuySellFormat(csvData[0])){			
			return true;
		}else if(!hasPriceInDouble(csvData[1])){
			return true;
		}else if(!hasLotSizeInInt(csvData[2])){
			return true;
		}else if(!hasCorrectTimeStampFormat(csvData[3])){
			return true;
		}
		return false;
	}

	private static boolean hasCorrectTimeStampFormat(String string) {
		// TODO Auto-generated method stub
		try{
			DateTimeUtil.stringToTimestamp(string);
			return true;
		} catch (ParseException e) {
			return false;
		}
		
	}

	private static boolean hasLotSizeInInt(String string) {
		// TODO Auto-generated method stub
		try{
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
            return false;
		}
	}

	private static boolean hasPriceInDouble(String string) {
		// TODO Auto-generated method stub
		try{
			Double.parseDouble(string);
			return true;
		} catch (NumberFormatException e) {
            return false;
		}
	}

	private static boolean hasCorrectBuySellFormat(String string) {
		// TODO Auto-generated method stub
		String letters[] = string.split("");
		if(letters.length != 7){
			return false;
		} else if(!letters[3].equals("/")){
			return false;
		} else{
			Currency buy, sell;
			String[] cur = string.split("/");
			int count=0;
	        for (Currency c : Currency.values()) {
	        	  if(c.name().equals(cur[0])){
	        		  count++;
	        		  sell = c;
	        	  } else if(c.name().equals(cur[1])){
	        		  count++;
	        		  buy = c;
	        	  } 
	        } 
	        if(count == 2){
	        	return true;
	        }else{
	        	return false;
	        }
	    }
	}
}
