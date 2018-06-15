package com.mrbt.lingmoney.model.pdfProperties;
/**
 * 稳盈宝计划授权委托书动态属性
 * @author wzy
 */
public class TemplateWB {
	
	private String entrustmentPerson;//委托人
	private String cardNumber;//身份证号
	private String accountNumber;//领钱平台账号
	private String loanAmountD;//出借金额大写
	private String loanAmount;//出借金额
	private String timeString;//创建日期
	public String getEntrustmentPerson() {
		return entrustmentPerson;
	}
	public void setEntrustmentPerson(String entrustmentPerson) {
		this.entrustmentPerson = entrustmentPerson;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getLoanAmountD() {
		return loanAmountD;
	}
	public void setLoanAmountD(String loanAmountD) {
		this.loanAmountD = loanAmountD;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getTimeString() {
		return timeString;
	}
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

}
