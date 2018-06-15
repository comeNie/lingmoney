package com.mrbt.lingmoney.admin.vo.trading;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * @author Administrator
 *
 */
@XStreamAlias("request")
public class Query extends XmlBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2075267634289614997L;

	@XStreamAsAttribute
	@XStreamAlias("platformNo")
	private String platformNo;

	private String requestNo;

	private String mode;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

}
