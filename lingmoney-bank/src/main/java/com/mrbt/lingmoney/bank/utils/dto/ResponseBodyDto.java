package com.mrbt.lingmoney.bank.utils.dto;

/**
 * 应答报文body参数数据传输对象
 * 
 * @author YJQ
 *
 */
public class ResponseBodyDto {
	private String RETURNCODE = "000000";
	private String RETURNMSG = "交易成功";
	private String OLDREQSEQNO;

	public String getRETURNCODE() {
		return RETURNCODE;
	}

	public void setRETURNCODE(String rETURNCODE) {
		RETURNCODE = rETURNCODE;
	}

	public String getRETURNMSG() {
		return RETURNMSG;
	}

	public void setRETURNMSG(String rETURNMSG) {
		RETURNMSG = rETURNMSG;
	}

	public String getOLDREQSEQNO() {
		return OLDREQSEQNO;
	}

	public void setOLDREQSEQNO(String oLDREQSEQNO) {
		OLDREQSEQNO = oLDREQSEQNO;
	}

}
