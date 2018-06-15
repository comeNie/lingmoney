package com.mrbt.lingmoney.bank.deal.dto;

import com.mrbt.lingmoney.bank.HxBaseData;

/**
 * 还款收益结果查询请求dto
 * @author YJQ
 *
 */
public class HxQuerySubmitPaymentReqDto {
	private String MERCHANTID = HxBaseData.MERCHANTID;
	private String APPID = "PC";
	private String OLDREQSEQNO = "";
	private String LOANNO = "";
	private String SUBSEQNO = "";
	
	public String getMERCHANTID() {
		return MERCHANTID;
	}
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
	public String getLOANNO() {
		return LOANNO;
	}
	public void setLOANNO(String lOANNO) {
		LOANNO = lOANNO;
	}
	public String getSUBSEQNO() {
		return SUBSEQNO;
	}
	public void setSUBSEQNO(String sUBSEQNO) {
		SUBSEQNO = sUBSEQNO;
	}
}
