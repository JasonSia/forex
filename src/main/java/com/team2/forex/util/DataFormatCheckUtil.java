package com.team2.forex.util;

import java.text.ParseException;

import com.team2.forex.entity.Currency;

public class DataFormatCheckUtil {
	
	public static boolean checkData(String [] csvData){		
		
		if(!hasCorrectBuySellFormat(csvData[0])){
			//System.out.println("buy sell");			
			return true;
		}else if(!hasPriceInDouble(csvData[1])){
			//System.out.println("price");
			return true;
		}else if(!hasLotSizeInInt(csvData[2])){
			//System.out.println("size");
			return true;
		}else if(!hasCorrectTimeStampFormat(csvData[3])){
			//System.out.println("time");
			return true;
		}
		return false;
	}

	private static boolean hasCorrectTimeStampFormat(String string) {
		// TODO Auto-generated method stub
		try{
			DateTimeUtil.stringToTimestamp(string);
			System.out.println("right time");
			return true;
		} catch (ParseException e) {
			System.out.println("wrong time");
			return false;
		}
		
	}

	private static boolean hasLotSizeInInt(String string) {
		// TODO Auto-generated method stub
		try{
			Integer.parseInt(string);
			System.out.println("right int");
			return true;
		} catch (NumberFormatException e) {
			System.out.println("wrong int");
            return false;
		}
	}

	private static boolean hasPriceInDouble(String string) {
		// TODO Auto-generated method stub
		try{
			Double.parseDouble(string);
			System.out.println("right double");
			return true;
		} catch (NumberFormatException e) {
			System.out.println("wrong double");
            return false;
		}
	}

	private static boolean hasCorrectBuySellFormat(String string) {
		// TODO Auto-generated method stub
		String letters[] = string.split("");
		if(letters.length != 7){
			System.out.println("number of digits");
			return false;
		} else if(!letters[3].equals("/")){
			System.out.println("slash in between");
			return false;
		} else{
			System.out.println("buy sell");
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
