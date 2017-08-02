package com.team2.forex.entity;

import java.sql.Timestamp;

public class HistoricalAudit {
	private int historicalAuditId;
	private int fileRowNum;
	private String fileName;
	private double processingTime;
	
	public HistoricalAudit(int historicalAuditId, int fileRowNum, String fileName, double processingTime){
		this.historicalAuditId = historicalAuditId;
		this.fileRowNum = fileRowNum;
		this.fileName = fileName;
		this.processingTime = processingTime;
	}
	public HistoricalAudit(int fileRowNum, String fileName, double processingTime){
		this.fileRowNum = fileRowNum;
		this.fileName = fileName;
		this.processingTime = processingTime;
	}

	public int getHistoricalAuditId() {
		return historicalAuditId;
	}

	public void setHistoricalAuditId(int historicalAuditId) {
		this.historicalAuditId = historicalAuditId;
	}

	public int getFileRowNum() {
		return fileRowNum;
	}

	public void setFileRowNum(int fileRowNum) {
		this.fileRowNum = fileRowNum;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(double processingTime) {
		this.processingTime = processingTime;
	}
}
