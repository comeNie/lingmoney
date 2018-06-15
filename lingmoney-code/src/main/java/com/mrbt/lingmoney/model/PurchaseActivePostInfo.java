package com.mrbt.lingmoney.model;

public class PurchaseActivePostInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tId;
	private String financialMoney;
	private String bizCode;
	private String infoId;
	private String status;
	public String gettId() {
		return tId;
	}
	public void settId(String tId) {
		this.tId = tId;
	}
	public String getFinancialMoney() {
		return financialMoney;
	}
	public void setFinancialMoney(String financialMoney) {
		this.financialMoney = financialMoney;
	}
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
