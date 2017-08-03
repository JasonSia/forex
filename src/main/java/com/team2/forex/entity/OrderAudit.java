package com.team2.forex.entity;

import java.sql.Timestamp;
import java.text.ParseException;

import com.team2.forex.util.DateTimeUtil;

public class OrderAudit {

	private int recordId;
	private int orderId;
	private String orderNumber;
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
	private String userid;
	private Timestamp modifiedTime;
	
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Timestamp getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
}