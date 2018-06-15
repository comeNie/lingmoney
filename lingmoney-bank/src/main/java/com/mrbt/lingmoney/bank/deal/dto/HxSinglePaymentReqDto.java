package com.mrbt.lingmoney.bank.deal.dto;

import com.mrbt.lingmoney.bank.HxBaseData;

/**
 * 还款人单笔还款请求dto
 * @author YJQ
 *
 */
public class HxSinglePaymentReqDto {
	private String MERCHANTID = HxBaseData.MERCHANTID;
	private String MERCHANTNAME = HxBaseData.MERCHANTNAME;
	private String APPID = "PC";
	private String TTRANS = "5";
	private String DFFLAG = "1";
	private String OLDREQSEQNO = "";
	private String LOANNO = "";
	private String BWACNAME = "";
	private String BWACNO = "";
	private String AMOUNT = "";
	private String REMARK = "";
	private String RETURNURL = "";
	private String FEEAMT = "";

	public String getMERCHANTID() {
		return MERCHANTID;
	}

	/*
	 * public void setMERCHANTID(String mERCHANTID) { MERCHANTID = mERCHANTID; }
	 */

	public String getMERCHANTNAME() {
		return MERCHANTNAME;
	}

	/*
	 * public void setMERCHANTNAME(String mERCHANTNAME) { MERCHANTNAME =
	 * mERCHANTNAME; }
	 */

	public String getAPPID() {
		return APPID;
	}

	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}

	public String getTTRANS() {
		return TTRANS;
	}

	/*
	 * public void setTTRANS(String tTRANS) { TTRANS = tTRANS; }
	 */

	public String getDFFLAG() {
		return DFFLAG;
	}

	public void setDFFLAG(String dFFLAG) {
		DFFLAG = dFFLAG;
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

	public String getBWACNAME() {
		return BWACNAME;
	}

	public void setBWACNAME(String bWACNAME) {
		BWACNAME = bWACNAME;
	}

	public String getBWACNO() {
		return BWACNO;
	}

	public void setBWACNO(String bWACNO) {
		BWACNO = bWACNO;
	}

	public String getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getRETURNURL() {
		return RETURNURL;
	}

	public void setRETURNURL(String rETURNURL) {
		RETURNURL = rETURNURL;
	}

	public String getFEEAMT() {
		return FEEAMT;
	}

	public void setFEEAMT(String fEEAMT) {
		FEEAMT = fEEAMT;
	}

}
