package com.mrbt.lingmoney.bank.deal.dto;

import com.mrbt.lingmoney.bank.HxBaseData;

public class HxQueryPaymentReqDto {
	private String MERCHANTID = HxBaseData.MERCHANTID;
	private String APPID = "PC";
	private String OLDREQSEQNO = "";

	public String getMERCHANTID() {
		return MERCHANTID;
	}

	/*
	 * public void setMERCHANTID(String mERCHANTID) { MERCHANTID = mERCHANTID; }
	 */

	public String getAPPID() {
		return APPID;
	}

	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}


	public String getOLDREQSEQNO() {
		return OLDREQSEQNO;
	}

	public void setOLDREQSEQNO(String oLDREQSEQNO) {
		OLDREQSEQNO = oLDREQSEQNO;
	}


}
