package com.team2.forex.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class OrderUtil {
	public static String generateOrderNumber(String userid) throws NoSuchAlgorithmException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String dateString = new Date().toString();
		return (new HexBinaryAdapter()).marshal(md5.digest((userid + dateString).getBytes()));
	}
}
