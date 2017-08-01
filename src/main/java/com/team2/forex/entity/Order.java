package com.team2.forex.entity;

import java.sql.Timestamp;

public class Order {

	private int orderId;
	private String orderType;
	private Currency currencyBuy;
	private Currency currencySell;
	private int size;
	private double preferredPrice;
	private double executedPrice;
	private String status;
	private Timestamp goodTillDate;
	private Timestamp submittedTime; 
	private Timestamp executedTime;
	private String userId;
	
	public Order(){}
	
	//initialising a new market order object here
	public Order(int orderId, String orderType, Currency currencyBuy, Currency currencySell, int size, Timestamp submittedTime, String userId){
		this.orderId=orderId;
		this.orderType=orderType;
		this.currencyBuy=currencyBuy;
		this.currencySell=currencySell;
		this.size=size;
		this.submittedTime=submittedTime;
		this.userId=userId;
	}
	
	//to do: initialise a new limit order object
	
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
	public void setCurrencyBuy(Currency currencyBuy) {
		this.currencyBuy = currencyBuy;
	}
	public Currency getCurrencySell() {
		return currencySell;
	}
	public void setCurrencySell(Currency currencySell) {
		this.currencySell = currencySell;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getGoodTillDate() {
		return goodTillDate;
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
