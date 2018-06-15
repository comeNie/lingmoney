package com.mrbt.lingmoney.bank.deal.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 还款收益结果查询回应dto
 * 
 * @author YJQ
 *
 */
public class HxQuerySubmitPaymentResDto {

	@JsonProperty(value = "RETURN_STATUS")
	private String RETURN_STATUS;

	@JsonProperty(value = "ERRORMSG")
	private String ERRORMSG;

	@JsonProperty(value = "OLDREQSEQNO")
	private String OLDREQSEQNO;

	@JsonProperty(value = "DFFLAG")
	private String DFFLAG;

	@JsonProperty(value = "LOANNO")
	private String LOANNO;

	@JsonProperty(value = "BWACNAME")
	private String BWACNAME;

	@JsonProperty(value = "BWACNO")
	private String BWACNO;

	@JsonProperty(value = "TOTALNUM")
	private String TOTALNUM;

	@JsonProperty(value = "RSLIST")
	private List<RepayListResDto> RSLIST = null;

	@JsonIgnore
	public String getRETURN_STATUS() {
		return RETURN_STATUS;
	}

	@JsonIgnore
	public void setRETURN_STATUS(String rETURN_STATUS) {
		RETURN_STATUS = rETURN_STATUS;
	}

	@JsonIgnore
	public String getERRORMSG() {
		return ERRORMSG;
	}

	@JsonIgnore
	public void setERRORMSG(String eRRORMSG) {
		ERRORMSG = eRRORMSG;
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
	public String getDFFLAG() {
		return DFFLAG;
	}

	@JsonIgnore
	public void setDFFLAG(String dFFLAG) {
		DFFLAG = dFFLAG;
	}

	@JsonIgnore
	public String getLOANNO() {
		return LOANNO;
	}

	@JsonIgnore
	public void setLOANNO(String lOANNO) {
		LOANNO = lOANNO;
	}

	@JsonIgnore
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
	public String getTOTALNUM() {
		return TOTALNUM;
	}

	@JsonIgnore
	public void setTOTALNUM(String tOTALNUM) {
		TOTALNUM = tOTALNUM;
	}

	@JsonIgnore
	public List<RepayListResDto> getRSLIST() {
		return RSLIST;
	}

	@JsonIgnore
	public void setRSLIST(List<RepayListResDto> rSLIST) {
		RSLIST = rSLIST;
	}

}
