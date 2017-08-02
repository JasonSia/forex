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
	//private String currencyBuyInput;
	//private String currencySellInput;
	private String orderNumber;
	
	/*public String getCurrencyBuyInput() {
		return currencyBuyInput;
	}

	public void setCurrencyBuyInput(String currencyBuyInput) {
		this.currencyBuyInput = currencyBuyInput;
	}

	public String getCurrencySellInput() {
		return currencySellInput;
	}

	public void setCurrencySellInput(String currencySellInput) {
		this.currencySellInput = currencySellInput;
	}*/

	public Order(){}
	
	//initialising a new market order object here -- edit: not req since POSTMAN POST creates object directly
	/*public Order(int orderId, String orderType, Currency currencyBuy, Currency currencySell, int size, Timestamp submittedTime, String userId){
		this.orderId=orderId;
		this.orderType=orderType;
		this.currencyBuy=currencyBuy;
		this.currencySell=currencySell;
		this.size=size;
		this.submittedTime=submittedTime;
		this.userId=userId;
	}*/
	
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
		this.currencyBuy = Currency.valueOf(currencyBuyInput);
	}
	public Currency getCurrencySell() {
		return currencySell;
	}
	public void setCurrencySell(String currencySellInput) {
		this.currencySell = Currency.valueOf(currencySellInput);
	}
	
	/*public String getCurrencyBuy() {
		return currencyBuy;
	}
	public void setCurrencyBuy(String currencyBuy) {
		this.currencyBuy = currencyBuy;
	}
	public String getCurrencySell() {
		return currencySell;
	}
	public void setCurrencySell(String currencySell) {
		this.currencySell = currencySell;
	}*/
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
