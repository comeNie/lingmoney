package com.mrbt.lingmoney.bank.deal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 收益明细查询循环列表(接收查询结果)
 * 
 * @author YJQ
 *
 */
public class RepayListResDto extends RepayListReqDto {
	@JsonProperty(value = "ACNO")
	private String ACNO;
	@JsonProperty(value = "STATUS")
	private String STATUS;
	@JsonProperty(value = "ERRMSG")
	private String ERRMSG;
	@JsonProperty(value = "HOSTDT")
	private String HOSTDT;
	@JsonProperty(value = "HOSTJNLNO")
	private String HOSTJNLNO;

	@JsonIgnore
	public String getACNO() {
		return ACNO;
	}

	@JsonIgnore
	public void setACNO(String aCNO) {
		ACNO = aCNO;
	}

	@JsonIgnore
	public String getSTATUS() {
		return STATUS;
	}

	@JsonIgnore
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	@JsonIgnore
	public String getERRMSG() {
		return ERRMSG;
	}

	@JsonIgnore
	public void setERRMSG(String eRRMSG) {
		ERRMSG = eRRMSG;
	}

	@JsonIgnore
	public String getHOSTDT() {
		return HOSTDT;
	}

	@JsonIgnore
	public void setHOSTDT(String hOSTDT) {
		HOSTDT = hOSTDT;
	}

	@JsonIgnore
	public String getHOSTJNLNO() {
		return HOSTJNLNO;
	}

	@JsonIgnore
	public void setHOSTJNLNO(String hOSTJNLNO) {
		HOSTJNLNO = hOSTJNLNO;
	}

}
