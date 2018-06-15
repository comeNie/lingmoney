package com.mrbt.lingmoney.admin.vo.trading;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * @author Administrator
 *
 */
@XStreamAlias("notify")
public class UserTransferAccountsNotifyResult extends XmlBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -348014228432204872L;
	/**
	 * 商户编号
	 */
	@XStreamAsAttribute
	@XStreamAlias("platformNo")
	private String platFormNo;
	private String requestNo;
	private String bizType;
	private String code;
	private String message;
	private String status;

	public String getPlatFormNo() {
		return platFormNo;
	}

	public void setPlatFormNo(String platFormNo) {
		this.platFormNo = platFormNo;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
