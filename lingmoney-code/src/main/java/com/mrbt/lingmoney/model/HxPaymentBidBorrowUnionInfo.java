package com.mrbt.lingmoney.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * hx_payment/hx_borrower_info/hx_borrower 连表查询model
 * @author YJQ
 *
 */
public class HxPaymentBidBorrowUnionInfo {
	
	// #start hx_payment
	
	 /**
     * 主键
     * 表字段 : hx_payment.id
     */
    private String id;

    /**
     * hx_borrower_info.id.id
     * 表字段 : hx_payment.bw_id
     */
    private String bwId;

    /**
     * 还款流水号
     * 表字段 : hx_payment.channel_flow
     */
    private String channelFlow;

    /**
     * 原垫付请求流水号，payment_type=2时，此项必填
     * 表字段 : hx_payment.old_channel_flow
     */
    private String oldChannelFlow;

    /**
     * 还款类别 1-个人普通还款 2-垫付后，借款人还款 3-公司垫付还款 4-自动还款
     * 表字段 : hx_payment.payment_type
     */
    private Integer paymentType;

    /**
     * 还款状态 1-还款成功 2- 还款中 3-还款失败
     * 表字段 : hx_payment.status
     */
    private Integer status;

    /**
     * 操作时间
     * 表字段 : hx_payment.payment_date
     */
    private Date paymentDate;

    /**
     * 扣借款人的平台手续费
     * 表字段 : hx_payment.fee_amt
     */
    private BigDecimal feeAmt;

    /**
     * 银行交易流水
     * 表字段 : hx_payment.bank_flow
     */
    private String bankFlow;

    /**
     * 还款金额
     * 表字段 : hx_payment.amount
     */
    private BigDecimal amount;
    
    /**
     * 收益明细提交状态 0-未提交 1-已提交 2-提交中 
     * 表字段 : hx_payment.submit_detail_status
     */
    private Integer submitDetailStatus;
    
    /**
     * 收益明细提交流水号
     * 表字段 : hx_payment.submit_channel_flow
     */
    private String submitChannelFlow;
    
    /**
     * 计息天数
     * 表字段 : hx_payment.days
     */
    private Integer days;
    
    // #end hx_payment
    
    // #start hx_borrower_info
    
    /**
     * 借款金额
     * 表字段 : hx_borrower_info.bw_amt
     */
    private BigDecimal bwAmt;
    
    /**
     * 借款人抵押品编号
     * 表字段 : hx_borrower_info.mortgage_id
     */
    private String mortgageId;
    
    // #end hx_borrower_info
    
    // #start hx_borrower
    /**
     * 借款人姓名
     * 表字段 : hx_borrower.bw_acname
     */
    private String bwAcname;

    /**
     * 借款人账号
     * 表字段 : hx_borrower.bw_acno
     */
    private String bwAcno;


    /**
     * 借款人账号所属行名
     * 表字段 : hx_borrower.bw_acbankname
     */
    private String bwAcbankname;
    /**
     * 用户id
     * 表字段 : hx_account.u_id
     */
    private String uId;
    
    // #end
    
    // #start hx_bidding
    /**
     * 借款编号
     * 表字段 : hx_bidding.loan_no
     */
    private String loanNo;
   
    // #end
    
    // #start product
    /**
     * 关联产品id
     * 表字段 : hx_bidding.p_id
     */
    private Integer pId;
    /**
     * 产品名称
     * 表字段 : product.name
     */
    private String name; // 取自产品表 name
    // #end

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBwId() {
		return bwId;
	}

	public void setBwId(String bwId) {
		this.bwId = bwId;
	}

	public String getChannelFlow() {
		return channelFlow;
	}

	public void setChannelFlow(String channelFlow) {
		this.channelFlow = channelFlow;
	}

	public String getOldChannelFlow() {
		return oldChannelFlow;
	}

	public void setOldChannelFlow(String oldChannelFlow) {
		this.oldChannelFlow = oldChannelFlow;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getBankFlow() {
		return bankFlow;
	}

	public void setBankFlow(String bankFlow) {
		this.bankFlow = bankFlow;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getBwAcname() {
		return bwAcname;
	}

	public void setBwAcname(String bwAcname) {
		this.bwAcname = bwAcname;
	}


	public String getBwAcno() {
		return bwAcno;
	}

	public void setBwAcno(String bwAcno) {
		this.bwAcno = bwAcno;
	}


	public String getBwAcbankname() {
		return bwAcbankname;
	}

	public void setBwAcbankname(String bwAcbankname) {
		this.bwAcbankname = bwAcbankname;
	}

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSubmitDetailStatus() {
		return submitDetailStatus;
	}

	public void setSubmitDetailStatus(Integer submitDetailStatus) {
		this.submitDetailStatus = submitDetailStatus;
	}

	public String getSubmitChannelFlow() {
		return submitChannelFlow;
	}

	public void setSubmitChannelFlow(String submitChannelFlow) {
		this.submitChannelFlow = submitChannelFlow;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
	
}
