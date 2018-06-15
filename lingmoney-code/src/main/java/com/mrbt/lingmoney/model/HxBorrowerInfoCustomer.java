package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 华兴银行标的借款人自定义vo
 *
 * @author lihq
 * @date 2017年6月8日 下午2:35:53
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class HxBorrowerInfoCustomer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7556890915314668255L;
	/**
	 * 主键 表字段 : hx_borrower_info.id
	 */
	private String id;
	/**
	 * 账户id 关联hx_account表
	 */
	private String accId;
	/**
	 * 借款人姓名 表字段 : hx_borrower.bw_acname
	 */
	private String bwAcname;
	/**
	 * 借款人手机号 表字段 : hx_account.mobile
	 */
	private String mobile;

	/**
	 * 借款人证件类型，身份证：1010 表字段 : hx_borrower.bw_idtype
	 */
	private String bwIdtype;

	/**
	 * 借款人证件号码，18位身份证 表字段 : hx_borrower.bw_idno
	 */
	private String bwIdno;

	/**
	 * 借款人账号 表字段 : hx_borrower.bw_acno
	 */
	private String bwAcno;

	/**
	 * 借款人账号所属行号，12位联行号，12位数字 表字段 : hx_borrower.bw_acbankid
	 */
	private String bwAcbankid;

	/**
	 * 借款人账号所属行名 表字段 : hx_borrower.bw_acbankname
	 */
	private String bwAcbankname;
	/**
	 * 状态。0，不可用；1，可用；-1，删除 表字段 : hx_borrower.status
	 */
	private Integer status;
	/**
	 * 借款人id，关联hw_borrower表id 表字段 : hx_borrower_info.bw_id
	 */
	private String bwId;
	/**
	 * 关联标的id 表字段 : hx_borrower_info.bidding_id
	 */
	private String biddingId;
	/**
	 * 借款金额 表字段 : hx_borrower_info.bw_amt
	 */
	private BigDecimal bwAmt;

	/**
	 * 借款人抵押品编号 表字段 : hx_borrower_info.mortgage_id
	 */
	private String mortgageId;

	/**
	 * 借款人抵押品简单描述 表字段 : hx_borrower_info.mortgage_info
	 */
	private String mortgageInfo;

	/**
	 * 借款人审批通过日期，YYYYMMDD 表字段 : hx_borrower_info.check_date
	 */
	private String checkDate;

	/**
	 * 备注（其它未尽事宜） 表字段 : hx_borrower_info.remark
	 */
	private String remark;

	/**
	 * 借款人年龄 表字段 : hx_borrower.bw_age
	 */
	private Integer bwAge;

	/**
	 * 借款人婚姻状态 表字段 : hx_borrower.bw_marriage
	 */
	private String bwMarriage;

	/**
	 * 借款人性别 0男 1女 表字段 : hx_borrower.bw_sex
	 */
	private String bwSex;

	/**
	 * 借款人信用记录 表字段 : hx_borrower.bw_credit_record
	 */
	private String bwCreditRecord;

	/**
	 * 借款人籍贯 表字段 : hx_borrower.bw_orgin_place
	 */
	private String bwOrginPlace;

	/**
	 * 借款用途 表字段 : hx_borrower_info.bw_use
	 */
	private String bwUse;
	/**
	 * 借款描述 表字段 : hx_borrower_info.bw_info
	 */
	private String bwInfo;
	/**
	 * 抵押物所有权人 表字段 : hx_borrower_info.mortgage_owner
	 */
	private String mortgageOwner;

	/**
	 * 抵押物共有情况 表字段 : hx_borrower_info.mortgage_common
	 */
	private String mortgageCommon;

	/**
	 * 抵押物所有权取得方式 表字段 : hx_borrower_info.mortgage_own_style
	 */
	private String mortgageOwnStyle;

	/**
	 * 抵押物所属地域 表字段 : hx_borrower_info.mortgage_region
	 */
	private String mortgageRegion;

	/**
	 * 抵押物类型 表字段 : hx_borrower_info.mortgage_type
	 */
	private String mortgageType;

	/**
	 * 抵押物购买价格 表字段 : hx_borrower_info.mortgage_buy_price
	 */
	private String mortgageBuyPrice;

	/**
	 * 抵押物评估价格 表字段 : hx_borrower_info.mortgage_evaluate_price
	 */
	private String mortgageEvaluatePrice;

	/**
	 * 抵押物抵押率 表字段 : hx_borrower_info.mortgage_rate
	 */
	private String mortgageRate;

	public String getBwAcname() {
		return bwAcname;
	}

	public void setBwAcname(String bwAcname) {
		this.bwAcname = bwAcname;
	}

	public String getBwIdtype() {
		return bwIdtype;
	}

	public void setBwIdtype(String bwIdtype) {
		this.bwIdtype = bwIdtype;
	}

	public String getBwIdno() {
		return bwIdno;
	}

	public void setBwIdno(String bwIdno) {
		this.bwIdno = bwIdno;
	}

	public String getBwAcno() {
		return bwAcno;
	}

	public void setBwAcno(String bwAcno) {
		this.bwAcno = bwAcno;
	}

	public String getBwAcbankid() {
		return bwAcbankid;
	}

	public void setBwAcbankid(String bwAcbankid) {
		this.bwAcbankid = bwAcbankid;
	}

	public String getBwAcbankname() {
		return bwAcbankname;
	}

	public void setBwAcbankname(String bwAcbankname) {
		this.bwAcbankname = bwAcbankname;
	}

	public BigDecimal getBwAmt() {
		return bwAmt;
	}

	public void setBwAmt(BigDecimal bwAmt) {
		this.bwAmt = bwAmt;
	}

	public String getMortgageId() {
		return mortgageId;
	}

	public void setMortgageId(String mortgageId) {
		this.mortgageId = mortgageId;
	}

	public String getMortgageInfo() {
		return mortgageInfo;
	}

	public void setMortgageInfo(String mortgageInfo) {
		this.mortgageInfo = mortgageInfo;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBiddingId() {
		return biddingId;
	}

	public void setBiddingId(String biddingId) {
		this.biddingId = biddingId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getBwId() {
		return bwId;
	}

	public void setBwId(String bwId) {
		this.bwId = bwId;
	}

	public Integer getBwAge() {
		return bwAge;
	}

	public void setBwAge(Integer bwAge) {
		this.bwAge = bwAge;
	}

	public String getBwMarriage() {
		return bwMarriage;
	}

	public void setBwMarriage(String bwMarriage) {
		this.bwMarriage = bwMarriage;
	}

	public String getBwSex() {
		return bwSex;
	}

	public void setBwSex(String bwSex) {
		this.bwSex = bwSex;
	}

	public String getBwCreditRecord() {
		return bwCreditRecord;
	}

	public void setBwCreditRecord(String bwCreditRecord) {
		this.bwCreditRecord = bwCreditRecord;
	}

	public String getBwOrginPlace() {
		return bwOrginPlace;
	}

	public void setBwOrginPlace(String bwOrginPlace) {
		this.bwOrginPlace = bwOrginPlace;
	}

	public String getBwUse() {
		return bwUse;
	}

	public void setBwUse(String bwUse) {
		this.bwUse = bwUse;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBwInfo() {
		return bwInfo;
	}

	public void setBwInfo(String bwInfo) {
		this.bwInfo = bwInfo;
	}

	public String getMortgageOwner() {
		return mortgageOwner;
	}

	public void setMortgageOwner(String mortgageOwner) {
		this.mortgageOwner = mortgageOwner;
	}

	public String getMortgageCommon() {
		return mortgageCommon;
	}

	public void setMortgageCommon(String mortgageCommon) {
		this.mortgageCommon = mortgageCommon;
	}

	public String getMortgageOwnStyle() {
		return mortgageOwnStyle;
	}

	public void setMortgageOwnStyle(String mortgageOwnStyle) {
		this.mortgageOwnStyle = mortgageOwnStyle;
	}

	public String getMortgageRegion() {
		return mortgageRegion;
	}

	public void setMortgageRegion(String mortgageRegion) {
		this.mortgageRegion = mortgageRegion;
	}

	public String getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}

	public String getMortgageBuyPrice() {
		return mortgageBuyPrice;
	}

	public void setMortgageBuyPrice(String mortgageBuyPrice) {
		this.mortgageBuyPrice = mortgageBuyPrice;
	}

	public String getMortgageEvaluatePrice() {
		return mortgageEvaluatePrice;
	}

	public void setMortgageEvaluatePrice(String mortgageEvaluatePrice) {
		this.mortgageEvaluatePrice = mortgageEvaluatePrice;
	}

	public String getMortgageRate() {
		return mortgageRate;
	}

	public void setMortgageRate(String mortgageRate) {
		this.mortgageRate = mortgageRate;
	}

}
