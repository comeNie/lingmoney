package com.mrbt.lingmoney.bank.deal.dto;

import java.util.List;

import com.mrbt.lingmoney.bank.HxBaseData;

/**
 * 还款收益明细提交数据传输对象
 * 
 * @author YJQ
 *
 */
public class HxSubmitPaymentGainsReqDto {
	private String MERCHANTID = HxBaseData.MERCHANTID;
	private String APPID = "PC";
	private String OLDREQSEQNO = "";
	private String DFFLAG = "";
	private String LOANNO = "";
	private String BWACNAME = "";
	private String BWACNO = "";
	private String TOTALNUM = "1";
	private List<RepayListReqDto> REPAYLIST = null;
	public String getMERCHANTID() {
		return MERCHANTID;
	}
	public void setMERCHANTID(String mERCHANTID) {
		MERCHANTID = mERCHANTID;
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
	public String getDFFLAG() {
		return DFFLAG;
	}
	public void setDFFLAG(String dFFLAG) {
		DFFLAG = dFFLAG;
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
	public String getTOTALNUM() {
		return TOTALNUM;
	}
	public void setTOTALNUM(String tOTALNUM) {
		TOTALNUM = tOTALNUM;
	}
	public List<RepayListReqDto> getREPAYLIST() {
		return REPAYLIST;
	}
	public void setREPAYLIST(List<RepayListReqDto> rEPAYLIST) {
		REPAYLIST = rEPAYLIST;
	}
	
}
