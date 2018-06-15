package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 华兴银行投标人信息
 *
 * @author lihq
 * @date 2017年6月13日 下午5:54:22
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class HxBidderCustomer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3050801540066828926L;

	/**
	 * 交易ID
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private String uId;
	/**
	 * 投标人账号
	 */
	private String acNo;
	/**
	 * 投标人姓名
	 */
	private String acName;
	/**
	 * 标的ID
	 */
	private String bId;
	/**
	 * 投标金额
	 */
	private BigDecimal amount;
	/**
	 * 借款编号
	 */
	private String loanNo;
	/**
	 * 投标流水号
	 */
	private String bizCode;
	/**
	 * 状态
	 */
	private Integer status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getAcNo() {
		return acNo;
	}

	public void setAcNo(String acNo) {
		this.acNo = acNo;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
