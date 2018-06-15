package com.mrbt.lingmoney.bank.deal.dto;

import java.util.Date;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DateUtils;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;;


/**
 * 日终对账请求dto
 * @author YJQ
 *
 */
public class HxDailyReconciliateReqDto {
	private String MERCHANTID = HxBaseData.MERCHANTID;
	private String APPID = "PC";
	private String OPERFLAG = "0";
	// 获取前一天
	private String CHECKDATE = new HxBankDateUtils(DateUtils.addDay(new Date(), -1)).getNowDate();
	//private String CHECKDATE = "20170717";
	
	public String getMERCHANTID() {
		return MERCHANTID;
	}
	public String getAPPID() {
		return APPID;
	}
	public void setAPPID(String aPPID) {
		APPID = aPPID;
	}
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
	
	
}
