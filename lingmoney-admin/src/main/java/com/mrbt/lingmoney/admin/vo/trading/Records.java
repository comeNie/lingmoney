package com.mrbt.lingmoney.admin.vo.trading;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 */
@XStreamAlias("record")
public class Records extends XmlBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7345780957364087686L;

	private String bizType;

	private String status;

	private String subStatus;

	private String amount;

	private String requestNo;

	private String sourceUserType;
	private String sourceUserNo;
	private String createdTime;
	private String completedTime;

	private String userNo;
	private String createTime;

	private String payProduct;
	private String fee;
	private String feeMode;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPayProduct() {
		return payProduct;
	}

	public void setPayProduct(String payProduct) {
		this.payProduct = payProduct;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFeeMode() {
		return feeMode;
	}

	public void setFeeMode(String feeMode) {
		this.feeMode = feeMode;
	}

	public String getSourceUserType() {
		return sourceUserType;
	}

	public void setSourceUserType(String sourceUserType) {
		this.sourceUserType = sourceUserType;
	}

	public String getSourceUserNo() {
		return sourceUserNo;
	}

	public void setSourceUserNo(String sourceUserNo) {
		this.sourceUserNo = sourceUserNo;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

}
