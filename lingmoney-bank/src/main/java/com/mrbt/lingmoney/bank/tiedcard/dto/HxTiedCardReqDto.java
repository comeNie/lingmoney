package com.mrbt.lingmoney.bank.tiedcard.dto;

import com.mrbt.lingmoney.bank.HxBaseData;

/**
 * 请求绑卡参数dto
 * @author YJQ
 *
 */
public class HxTiedCardReqDto {
	private String MERCHANTID = HxBaseData.MERCHANTID;
	private String APPID = "PC";
	private String TTRANS = "";
	private String ACNO = "";
	private String RETURNURL = "";
	public String getMERCHANTID() {
		return MERCHANTID;
	}
	public String getAPPID() {
		return APPID;
	}
	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}
	public String getTTRANS() {
		return TTRANS;
	}
	public void setTTRANS(String tTRANS) {
		TTRANS = tTRANS;
	}
	public String getACNO() {
		return ACNO;
	}
	public void setACNO(String aCNO) {
		ACNO = aCNO;
	}
	public String getRETURNURL() {
		return RETURNURL;
	}
	public void setRETURNURL(String rETURNURL) {
		RETURNURL = rETURNURL;
	}
	
}
