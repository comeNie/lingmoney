package com.mrbt.lingmoney.bank.deal.dto;

import com.mrbt.lingmoney.bank.HxBaseData;

/**
 * 单标公司垫付还款请求dto
 * @author YJQ
 *
 */
public class HxCompanyAdvPaymentReqDto {
	private String MERCHANTID = HxBaseData.MERCHANTID;
	private String MERCHANTNAME = HxBaseData.MERCHANTNAME;
	private String APPID = "PC";
	private String LOANNO = "";
	private String BWACNAME = "";
	private String BWACNO = "";
	private String AMOUNT = "";
	private String REMARK = "";
	private String FEEAMT = "";
	public String getMERCHANTID() {
		return MERCHANTID;
	}
	public void setMERCHANTID(String mERCHANTID) {
		MERCHANTID = mERCHANTID;
	}
	public String getMERCHANTNAME() {
		return MERCHANTNAME;
	}
	public void setMERCHANTNAME(String mERCHANTNAME) {
		MERCHANTNAME = mERCHANTNAME;
	}
	public String getAPPID() {
		return APPID;
	}
	public void setAPPID(String aPPID) {
		APPID = aPPID;
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
	public String getFEEAMT() {
		return FEEAMT;
	}
	public void setFEEAMT(String fEEAMT) {
		FEEAMT = fEEAMT;
	} 
	
	
}
