package com.mrbt.lingmoney.model.trading;

import com.mrbt.lingmoney.model.UsersBank;

/**
 *@author syb
 *@date 2017年4月13日 下午5:57:15
 *@version 1.0
 *@description 银行信息 交易帮助类
 **/
public class BankInfo extends UsersBank{
	private static final long serialVersionUID = 1L;
	
	private String bankShort;
	private String bankLogo;
	private Integer bankType;
	private Integer bankOrder;
	private String upperLimit;
	private String everytimeLimit;
	private String everydayLimit;
	private String everymonthLimit;
	
	public BankInfo() {
	}
	
	public String getBankShort() {
		return bankShort;
	}
	public void setBankShort(String bankShort) {
		this.bankShort = bankShort;
	}
	public String getBankLogo() {
		return bankLogo;
	}
	public void setBankLogo(String bankLogo) {
		this.bankLogo = bankLogo;
	}
	public Integer getBankType() {
		return bankType;
	}
	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}
	public Integer getBankOrder() {
		return bankOrder;
	}
	public void setBankOrder(Integer bankOrder) {
		this.bankOrder = bankOrder;
	}
	public String getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}
	public String getEverytimeLimit() {
		return everytimeLimit;
	}
	public void setEverytimeLimit(String everytimeLimit) {
		this.everytimeLimit = everytimeLimit;
	}
	public String getEverydayLimit() {
		return everydayLimit;
	}
	public void setEverydayLimit(String everydayLimit) {
		this.everydayLimit = everydayLimit;
	}
	public String getEverymonthLimit() {
		return everymonthLimit;
	}
	public void setEverymonthLimit(String everymonthLimit) {
		this.everymonthLimit = everymonthLimit;
	}

}
