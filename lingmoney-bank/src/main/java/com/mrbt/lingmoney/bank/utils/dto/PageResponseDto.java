package com.mrbt.lingmoney.bank.utils.dto;

/**
 * 返回页面form表单的数据传输对象
 * @author YJQ
 *
 */
public class PageResponseDto {
	private String transCode;
	private String requestData;
	private String channelFlow;
	private String bankUrl;
	public String gettransCode() {
		return transCode;
	}
	public void settransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getrequestData() {
		return requestData;
	}
	public void setrequestData(String requestData) {
		this.requestData = requestData;
	}
	public String getchannelFlow() {
		return channelFlow;
	}
	public void setchannelFlow(String channelFlow) {
		this.channelFlow = channelFlow;
	}
	public String getbankUrl() {
		return bankUrl;
	}
	public void setbankUrl(String bankUrl) {
		this.bankUrl = bankUrl;
	}
	
}
