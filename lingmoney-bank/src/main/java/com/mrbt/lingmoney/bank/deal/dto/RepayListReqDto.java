package com.mrbt.lingmoney.bank.deal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 收益明细查询循环列表
 * 
 * @author YJQ
 *
 */
public class RepayListReqDto {
	@JsonProperty(value = "SUBSEQNO")
	private String SUBSEQNO = "";

	@JsonProperty(value = "ACNO")
	private String ACNO = "";

	@JsonProperty(value = "ACNAME")
	private String ACNAME = "";

	@JsonProperty(value = "INCOMEDATE")
	private String INCOMEDATE = "";

	@JsonProperty(value = "AMOUNT")
	private String AMOUNT = "";

	@JsonProperty(value = "PRINCIPALAMT")
	private String PRINCIPALAMT = "";

	@JsonProperty(value = "INCOMEAMT")
	private String INCOMEAMT = "";

	@JsonProperty(value = "FEEAMT")
	private String FEEAMT = "0";

	@JsonIgnore
	public String getSUBSEQNO() {
		return SUBSEQNO;
	}

	@JsonIgnore
	public void setSUBSEQNO(String sUBSEQNO) {
		SUBSEQNO = sUBSEQNO;
	}

	@JsonIgnore
	public String getACNAME() {
		return ACNAME;
	}

	@JsonIgnore
	public void setACNAME(String aCNAME) {
		ACNAME = aCNAME;
	}

	@JsonIgnore
	public String getINCOMEDATE() {
		return INCOMEDATE;
	}

	@JsonIgnore
	public void setINCOMEDATE(String iNCOMEDATE) {
		INCOMEDATE = iNCOMEDATE;
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
	public String getPRINCIPALAMT() {
		return PRINCIPALAMT;
	}

	@JsonIgnore
	public void setPRINCIPALAMT(String pRINCIPALAMT) {
		PRINCIPALAMT = pRINCIPALAMT;
	}

	@JsonIgnore
	public String getINCOMEAMT() {
		return INCOMEAMT;
	}

	@JsonIgnore
	public void setINCOMEAMT(String iNCOMEAMT) {
		INCOMEAMT = iNCOMEAMT;
	}

	@JsonIgnore
	public String getFEEAMT() {
		return FEEAMT;
	}

	@JsonIgnore
	public void setFEEAMT(String fEEAMT) {
		FEEAMT = fEEAMT;
	}

	@JsonIgnore
	public String getACNO() {
		return ACNO;
	}

	@JsonIgnore
	public void setACNO(String aCNO) {
		ACNO = aCNO;
	}

}
