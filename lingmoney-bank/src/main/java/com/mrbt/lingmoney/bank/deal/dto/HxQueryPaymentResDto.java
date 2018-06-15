package com.mrbt.lingmoney.bank.deal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 还款人单笔还款查询结果dto
 * 
 * @author YJQ
 *
 */
public class HxQueryPaymentResDto {
	@JsonProperty(value = "RESJNLNO")
	private String RESJNLNO;

	@JsonProperty(value = "OLDREQSEQNO")
	private String OLDREQSEQNO;

	@JsonProperty(value = "TRANSDT")
	private String TRANSDT;

	@JsonProperty(value = "TRANSTM")
	private String TRANSTM;

	@JsonProperty(value = "RETURN_STATUS")
	private String RETURN_STATUS;

	@JsonProperty(value = "LOANNO")
	private String LOANNO;

	@JsonProperty(value = "BWACNAME")
	private String BWACNAME;

	@JsonProperty(value = "BWACNO")
	private String BWACNO;

	@JsonProperty(value = "AMOUNT")
	private String AMOUNT;

	@JsonProperty(value = "ERRORMSG")
	private String ERRORMSG;
	
	@JsonProperty(value = "myStatus")
	private Integer myStatus;

	@JsonIgnore
	public String getRESJNLNO() {
		return RESJNLNO;
	}

	@JsonIgnore
	public void setRESJNLNO(String rESJNLNO) {
		RESJNLNO = rESJNLNO;
	}

	@JsonIgnore
	public String getOLDREQSEQNO() {
		return OLDREQSEQNO;
	}

	@JsonIgnore
	public void setOLDREQSEQNO(String oLDREQSEQNO) {
		OLDREQSEQNO = oLDREQSEQNO;
	}

	@JsonIgnore
	public String getTRANSDT() {
		return TRANSDT;
	}

	@JsonIgnore
	public void setTRANSDT(String tRANSDT) {
		TRANSDT = tRANSDT;
	}

	@JsonIgnore
	public String getTRANSTM() {
		return TRANSTM;
	}

	@JsonIgnore
	public void setTRANSTM(String tRANSTM) {
		TRANSTM = tRANSTM;
	}

	@JsonIgnore
	public String getRETURN_STATUS() {
		return RETURN_STATUS;
	}

	@JsonIgnore
	public void setRETURN_STATUS(String rETURN_STATUS) {
		RETURN_STATUS = rETURN_STATUS;
	}

	@JsonIgnore
	public String getLOANNO() {
		return LOANNO;
	}

	@JsonIgnore
	public void setLOANNO(String lOANNO) {
		LOANNO = lOANNO;
	}

	public String getBWACNAME() {
		return BWACNAME;
	}

	@JsonIgnore
	public void setBWACNAME(String bWACNAME) {
		BWACNAME = bWACNAME;
	}

	@JsonIgnore
	public String getBWACNO() {
		return BWACNO;
	}

	@JsonIgnore
	public void setBWACNO(String bWACNO) {
		BWACNO = bWACNO;
	}

	@JsonIgnore
	public String getAMOUNT() {
		return AMOUNT;
	}

	@JsonIgnore
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}

	@JsonIgnore
	public String getERRORMSG() {
		return ERRORMSG;
	}

	@JsonIgnore
	public void setERRORMSG(String eRRORMSG) {
		ERRORMSG = eRRORMSG;
	}

	public Integer getMyStatus() {
		return myStatus;
	}

	public void setMyStatus(Integer myStatus) {
		this.myStatus = myStatus;
	}
	
	
}
