package com.mrbt.lingmoney.bank.utils.dto;

import java.util.Date;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;

/**
 * 应答头数据传输对象
 * @author YJQ
 *
 */
public class ResponseHeadDto {
	private String channelCode = HxBaseData.CHANNEL_CODE;
	private String channelFlow = "";
	private String transCode = "";
	private String serverFlow = "";
	private String serverDate = new HxBankDateUtils(new Date()).getNowDate();
	private String serverTime = new HxBankDateUtils(new Date()).getNowTime();
	private String encryptData = "";
	private String status = "0";
	private String errorCode = "0";
	private String errorMsg = "";

	public String getchannelCode() {
		return channelCode;
	}

	/*
	 * private void setChannelCode(String channelCode) { this.channelCode =
	 * channelCode; }
	 */
	public String getchannelFlow() {
		return channelFlow;
	}

	public void setchannelFlow(String channelFlow) {
		this.channelFlow = channelFlow;
	}

	public String gettransCode() {
		return transCode;
	}

	public void settransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getserverFlow() {
		return serverFlow;
	}

	public void setserverFlow(String serverFlow) {
		this.serverFlow = serverFlow;
	}

	public String getserverDate() {
		return serverDate;
	}

	/*public void setServerDate(String serverDate) {
		this.serverDate = serverDate;
	}*/

	public String getserverTime() {
		return serverTime;
	}

	/*public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}*/

	public String getencryptData() {
		return encryptData;
	}

	/*public void setEncryptData(String encryptData) {
		this.encryptData = encryptData;
	}*/

	public String getstatus() {
		return status;
	}

	public void setstatus(String status) {
		this.status = status;
	}

	public String geterrorCode() {
		return errorCode;
	}

	public void seterrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String geterrorMsg() {
		return errorMsg;
	}

	public void seterrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
