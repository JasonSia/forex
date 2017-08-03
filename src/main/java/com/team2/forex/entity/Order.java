package com.team2.forex.entity;

import java.sql.Timestamp;
import java.text.ParseException;

import com.team2.forex.util.DateTimeUtil;

public class Order {

	private int orderId;
	private String orderType;
	private Currency currencyBuy;
	private Currency currencySell;
	private int size;
	private double preferredPrice;
	private double executedPrice;
	private Status status;
	private Timestamp goodTillDate;
	private Timestamp submittedTime; 
	private Timestamp executedTime;
	private String userId;
	private String orderNumber;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public Order(int orderId, String orderType, Currency currencyBuy, Currency currencySell, int size,
			double preferredPrice, double executedPrice, Status status, Timestamp goodTillDate, Timestamp submittedTime,
			Timestamp executedTime, String userId, String orderNumber) {
		
		this.orderId = orderId;
		this.orderType = orderType;
		this.currencyBuy = currencyBuy;
		this.currencySell = currencySell;
		this.size = size;
		this.preferredPrice = preferredPrice;
		this.executedPrice = executedPrice;
		this.status = status;
		this.goodTillDate = goodTillDate;
		this.submittedTime = submittedTime;
		this.executedTime = executedTime;
		this.userId = userId;
		this.orderNumber = orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Order(){}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Currency getCurrencyBuy() {
		return currencyBuy;
	}
	public void setCurrencyBuy(String currencyBuyInput) {
		int flag=0;
		for(Currency c: Currency.values()){
			if(currencyBuyInput.equalsIgnoreCase(c.name())){
				flag=1;
			}
		}
		if(flag==1)
		  this.currencyBuy = Currency.valueOf(currencyBuyInput);
		else
		  this.currencyBuy=null;
	}
	public Currency getCurrencySell() {
		return currencySell;
	}
	public void setCurrencySell(String currencySellInput) {
		int flag=0;
		for(Currency c: Currency.values()){
			if(currencySellInput.equalsIgnoreCase(c.name())){
				flag=1;
			}
		}
		if(flag==1)
		  this.currencySell = Currency.valueOf(currencySellInput);
		else
		  this.currencySell=null;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public double getPreferredPrice() {
		return preferredPrice;
	}
	public void setPreferredPrice(double preferredPrice) {
		this.preferredPrice = preferredPrice;
	}
	public double getExecutedPrice() {
		return executedPrice;
	}
	public void setExecutedPrice(double executedPrice) {
		this.executedPrice = executedPrice;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Timestamp getGoodTillDate() {
		return goodTillDate;
	}
	public void setGoodTillDate(String goodTillDateInput)throws ParseException {
		this.goodTillDate = DateTimeUtil.stringToTimestamp(goodTillDateInput);
	}
	public void setGoodTillDate(Timestamp goodTillDate) {
		this.goodTillDate = goodTillDate;
	}
	public Timestamp getSubmittedTime() {
		return submittedTime;
	}
	public void setSubmittedTime(Timestamp submittedTime) {
		this.submittedTime = submittedTime;
	}
	public Timestamp getExecutedTime() {
		return executedTime;
	}
	public void setExecutedTime(Timestamp executedTime) {
		this.executedTime = executedTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
