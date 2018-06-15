package com.mrbt.lingmoney.bank.deal.dto;

/**
 * 日终对账应答dto
 * @author YJQ
 *
 */
public class HxDailyReconciliateResDto {
	private String OPERFLAG;
	private String CHECKDATE;
	private String RETURN_STATUS;
	private String ERRORMSG;
	private String FILENAME;
	private String FILECONTEXT;
	public String getOPERFLAG() {
		return OPERFLAG;
	}
	public void setOPERFLAG(String oPERFLAG) {
		OPERFLAG = oPERFLAG;
	}
	public String getCHECKDATE() {
		return CHECKDATE;
	}
	public void setCHECKDATE(String cHECKDATE) {
		CHECKDATE = cHECKDATE;
	}
	public String getRETURN_STATUS() {
		return RETURN_STATUS;
	}
	public void setRETURN_STATUS(String rETURN_STATUS) {
		RETURN_STATUS = rETURN_STATUS;
	}
	public String getERRORMSG() {
		return ERRORMSG;
	}
	public void setERRORMSG(String eRRORMSG) {
		ERRORMSG = eRRORMSG;
	}
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getFILECONTEXT() {
		return FILECONTEXT;
	}
	public void setFILECONTEXT(String fILECONTEXT) {
		FILECONTEXT = fILECONTEXT;
	}
	
}
