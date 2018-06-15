package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.mrbt.lingmoney.commons.exception.PageInfoException;

public class HxDailyData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 表字段 : hx_daily_data.id
	 */
	private Integer id;

	/**
	 * 商户唯一编号 表字段 : hx_daily_data.merchant_id
	 */
	private String merchantId;

	/**
	 * 对账日期 表字段 : hx_daily_data.check_date
	 */
	private Date checkDate;

	/**
	 * 处理成功总笔数 表字段 : hx_daily_data.deal_num
	 */
	private Integer dealNum;

	/**
	 * 交易类型 1 投标 2撤标 3 流标 4放款 5 还款 20 专属账户充值 21 公司垫付 22投标优惠返回 23 单笔奖励或分红 24: 提现
	 * 30 自动投标 33 自动还款 表字段 : hx_daily_data.trade_type
	 */
	private Integer tradeType;

	/**
	 * 第三方父交易流水号 表字段 : hx_daily_data.channel_flow_father
	 */
	private String channelFlowFather;

	/**
	 * 第三方子流水号 表字段 : hx_daily_data.channel_flow_son
	 */
	private String channelFlowSon;

	/**
	 * 
	 * 表字段 : hx_daily_data.loan_no
	 */
	private String loanNo;

	/**
	 * 付款账号 表字段 : hx_daily_data.payment_ac_no
	 */
	private String paymentAcNo;

	/**
	 * 付款账号户名 表字段 : hx_daily_data.payment_ac_name
	 */
	private String paymentAcName;

	/**
	 * 付款行号 表字段 : hx_daily_data.payment_bank_no
	 */
	private String paymentBankNo;

	/**
	 * 付款行名 表字段 : hx_daily_data.payment_bank_name
	 */
	private String paymentBankName;

	/**
	 * 收款账号 表字段 : hx_daily_data.receipt_ac_no
	 */
	private String receiptAcNo;

	/**
	 * 收款账号户名 表字段 : hx_daily_data.receipt_ac_name
	 */
	private String receiptAcName;

	/**
	 * 收款行号 表字段 : hx_daily_data.receipt_bank_no
	 */
	private String receiptBankNo;

	/**
	 * 收款行名 表字段 : hx_daily_data.receipt_bank_name
	 */
	private String receiptBankName;

	/**
	 * 金额 表字段 : hx_daily_data.amount
	 */
	private BigDecimal amount;

	/**
	 * 手续费 表字段 : hx_daily_data.fee_amt
	 */
	private BigDecimal feeAmt;

	/**
	 * 账户管理费 表字段 : hx_daily_data.ac_custody_amt
	 */
	private BigDecimal acCustodyAmt;

	/**
	 * 风险保证金 表字段 : hx_daily_data.risk_assurance_amt
	 */
	private BigDecimal riskAssuranceAmt;

	/**
	 * 银行止付日期 表字段 : hx_daily_data.bank_stop_date
	 */
	private Date bankStopDate;

	/**
	 * 银行支付流水号 表字段 : hx_daily_data.bank_stop_flow
	 */
	private String bankStopFlow;

	/**
	 * 状态 1-可用 0-不可用 表字段 : hx_daily_data.status
	 */
	private Integer status;

	/**
	 * 创建时间 表字段 : hx_daily_data.create_time
	 */
	private Date createTime;

	/**
	 * 获取 字段:hx_daily_data.id
	 *
	 * @return hx_daily_data.id,
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置 字段:hx_daily_data.id
	 *
	 * @param id
	 *            the value for hx_daily_data.id,
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取 商户唯一编号 字段:hx_daily_data.merchant_id
	 *
	 * @return hx_daily_data.merchant_id, 商户唯一编号
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置 商户唯一编号 字段:hx_daily_data.merchant_id
	 *
	 * @param merchantId
	 *            the value for hx_daily_data.merchant_id, 商户唯一编号
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId == null ? null : merchantId.trim();
	}

	/**
	 * 获取 对账日期 字段:hx_daily_data.check_date
	 *
	 * @return hx_daily_data.check_date, 对账日期
	 */
	public Date getCheckDate() {
		return checkDate;
	}

	/**
	 * 设置 对账日期 字段:hx_daily_data.check_date
	 *
	 * @param checkDate
	 *            the value for hx_daily_data.check_date, 对账日期
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	/**
	 * 获取 处理成功总笔数 字段:hx_daily_data.deal_num
	 *
	 * @return hx_daily_data.deal_num, 处理成功总笔数
	 */
	public Integer getDealNum() {
		return dealNum;
	}

	/**
	 * 设置 处理成功总笔数 字段:hx_daily_data.deal_num
	 *
	 * @param dealNum
	 *            the value for hx_daily_data.deal_num, 处理成功总笔数
	 */
	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
	}

	/**
	 * 获取 交易类型 1 投标 2撤标 3 流标 4放款 5 还款 20 专属账户充值 21 公司垫付 22投标优惠返回 23 单笔奖励或分红 24:
	 * 提现 30 自动投标 33 自动还款 字段:hx_daily_data.trade_type
	 *
	 * @return hx_daily_data.trade_type, 交易类型 1 投标 2撤标 3 流标 4放款 5 还款 20 专属账户充值
	 *         21 公司垫付 22投标优惠返回 23 单笔奖励或分红 24: 提现 30 自动投标 33 自动还款
	 */
	public Integer getTradeType() {
		return tradeType;
	}

	/**
	 * 设置 交易类型 1 投标 2撤标 3 流标 4放款 5 还款 20 专属账户充值 21 公司垫付 22投标优惠返回 23 单笔奖励或分红 24:
	 * 提现 30 自动投标 33 自动还款 字段:hx_daily_data.trade_type
	 *
	 * @param tradeType
	 *            the value for hx_daily_data.trade_type, 交易类型 1 投标 2撤标 3 流标 4放款
	 *            5 还款 20 专属账户充值 21 公司垫付 22投标优惠返回 23 单笔奖励或分红 24: 提现 30 自动投标 33
	 *            自动还款
	 */
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * 获取 第三方父交易流水号 字段:hx_daily_data.channel_flow_father
	 *
	 * @return hx_daily_data.channel_flow_father, 第三方父交易流水号
	 */
	public String getChannelFlowFather() {
		return channelFlowFather;
	}

	/**
	 * 设置 第三方父交易流水号 字段:hx_daily_data.channel_flow_father
	 *
	 * @param channelFlowFather
	 *            the value for hx_daily_data.channel_flow_father, 第三方父交易流水号
	 */
	public void setChannelFlowFather(String channelFlowFather) {
		this.channelFlowFather = channelFlowFather == null ? null : channelFlowFather.trim();
	}

	/**
	 * 获取 第三方子流水号 字段:hx_daily_data.channel_flow_son
	 *
	 * @return hx_daily_data.channel_flow_son, 第三方子流水号
	 */
	public String getChannelFlowSon() {
		return channelFlowSon;
	}

	/**
	 * 设置 第三方子流水号 字段:hx_daily_data.channel_flow_son
	 *
	 * @param channelFlowSon
	 *            the value for hx_daily_data.channel_flow_son, 第三方子流水号
	 */
	public void setChannelFlowSon(String channelFlowSon) {
		this.channelFlowSon = channelFlowSon == null ? null : channelFlowSon.trim();
	}

	/**
	 * 获取 字段:hx_daily_data.loan_no
	 *
	 * @return hx_daily_data.loan_no,
	 */
	public String getLoanNo() {
		return loanNo;
	}

	/**
	 * 设置 字段:hx_daily_data.loan_no
	 *
	 * @param loanNo
	 *            the value for hx_daily_data.loan_no,
	 */
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo == null ? null : loanNo.trim();
	}

	/**
	 * 获取 付款账号 字段:hx_daily_data.payment_ac_no
	 *
	 * @return hx_daily_data.payment_ac_no, 付款账号
	 */
	public String getPaymentAcNo() {
		return paymentAcNo;
	}

	/**
	 * 设置 付款账号 字段:hx_daily_data.payment_ac_no
	 *
	 * @param paymentAcNo
	 *            the value for hx_daily_data.payment_ac_no, 付款账号
	 */
	public void setPaymentAcNo(String paymentAcNo) {
		this.paymentAcNo = paymentAcNo == null ? null : paymentAcNo.trim();
	}

	/**
	 * 获取 付款账号户名 字段:hx_daily_data.payment_ac_name
	 *
	 * @return hx_daily_data.payment_ac_name, 付款账号户名
	 */
	public String getPaymentAcName() {
		return paymentAcName;
	}

	/**
	 * 设置 付款账号户名 字段:hx_daily_data.payment_ac_name
	 *
	 * @param paymentAcName
	 *            the value for hx_daily_data.payment_ac_name, 付款账号户名
	 */
	public void setPaymentAcName(String paymentAcName) {
		this.paymentAcName = paymentAcName == null ? null : paymentAcName.trim();
	}

	/**
	 * 获取 付款行号 字段:hx_daily_data.payment_bank_no
	 *
	 * @return hx_daily_data.payment_bank_no, 付款行号
	 */
	public String getPaymentBankNo() {
		return paymentBankNo;
	}

	/**
	 * 设置 付款行号 字段:hx_daily_data.payment_bank_no
	 *
	 * @param paymentBankNo
	 *            the value for hx_daily_data.payment_bank_no, 付款行号
	 */
	public void setPaymentBankNo(String paymentBankNo) {
		this.paymentBankNo = paymentBankNo == null ? null : paymentBankNo.trim();
	}

	/**
	 * 获取 付款行名 字段:hx_daily_data.payment_bank_name
	 *
	 * @return hx_daily_data.payment_bank_name, 付款行名
	 */
	public String getPaymentBankName() {
		return paymentBankName;
	}

	/**
	 * 设置 付款行名 字段:hx_daily_data.payment_bank_name
	 *
	 * @param paymentBankName
	 *            the value for hx_daily_data.payment_bank_name, 付款行名
	 */
	public void setPaymentBankName(String paymentBankName) {
		this.paymentBankName = paymentBankName == null ? null : paymentBankName.trim();
	}

	/**
	 * 获取 收款账号 字段:hx_daily_data.receipt_ac_no
	 *
	 * @return hx_daily_data.receipt_ac_no, 收款账号
	 */
	public String getReceiptAcNo() {
		return receiptAcNo;
	}

	/**
	 * 设置 收款账号 字段:hx_daily_data.receipt_ac_no
	 *
	 * @param receiptAcNo
	 *            the value for hx_daily_data.receipt_ac_no, 收款账号
	 */
	public void setReceiptAcNo(String receiptAcNo) {
		this.receiptAcNo = receiptAcNo == null ? null : receiptAcNo.trim();
	}

	/**
	 * 获取 收款账号户名 字段:hx_daily_data.receipt_ac_name
	 *
	 * @return hx_daily_data.receipt_ac_name, 收款账号户名
	 */
	public String getReceiptAcName() {
		return receiptAcName;
	}

	/**
	 * 设置 收款账号户名 字段:hx_daily_data.receipt_ac_name
	 *
	 * @param receiptAcName
	 *            the value for hx_daily_data.receipt_ac_name, 收款账号户名
	 */
	public void setReceiptAcName(String receiptAcName) {
		this.receiptAcName = receiptAcName == null ? null : receiptAcName.trim();
	}

	/**
	 * 获取 收款行号 字段:hx_daily_data.receipt_bank_no
	 *
	 * @return hx_daily_data.receipt_bank_no, 收款行号
	 */
	public String getReceiptBankNo() {
		return receiptBankNo;
	}

	/**
	 * 设置 收款行号 字段:hx_daily_data.receipt_bank_no
	 *
	 * @param receiptBankNo
	 *            the value for hx_daily_data.receipt_bank_no, 收款行号
	 */
	public void setReceiptBankNo(String receiptBankNo) {
		this.receiptBankNo = receiptBankNo == null ? null : receiptBankNo.trim();
	}

	/**
	 * 获取 收款行名 字段:hx_daily_data.receipt_bank_name
	 *
	 * @return hx_daily_data.receipt_bank_name, 收款行名
	 */
	public String getReceiptBankName() {
		return receiptBankName;
	}

	/**
	 * 设置 收款行名 字段:hx_daily_data.receipt_bank_name
	 *
	 * @param receiptBankName
	 *            the value for hx_daily_data.receipt_bank_name, 收款行名
	 */
	public void setReceiptBankName(String receiptBankName) {
		this.receiptBankName = receiptBankName == null ? null : receiptBankName.trim();
	}

	/**
	 * 获取 金额 字段:hx_daily_data.amount
	 *
	 * @return hx_daily_data.amount, 金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置 金额 字段:hx_daily_data.amount
	 *
	 * @param amount
	 *            the value for hx_daily_data.amount, 金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取 手续费 字段:hx_daily_data.fee_amt
	 *
	 * @return hx_daily_data.fee_amt, 手续费
	 */
	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	/**
	 * 设置 手续费 字段:hx_daily_data.fee_amt
	 *
	 * @param feeAmt
	 *            the value for hx_daily_data.fee_amt, 手续费
	 */
	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	/**
	 * 获取 账户管理费 字段:hx_daily_data.ac_custody_amt
	 *
	 * @return hx_daily_data.ac_custody_amt, 账户管理费
	 */
	public BigDecimal getAcCustodyAmt() {
		return acCustodyAmt;
	}

	/**
	 * 设置 账户管理费 字段:hx_daily_data.ac_custody_amt
	 *
	 * @param acCustodyAmt
	 *            the value for hx_daily_data.ac_custody_amt, 账户管理费
	 */
	public void setAcCustodyAmt(BigDecimal acCustodyAmt) {
		this.acCustodyAmt = acCustodyAmt;
	}

	/**
	 * 获取 风险保证金 字段:hx_daily_data.risk_assurance_amt
	 *
	 * @return hx_daily_data.risk_assurance_amt, 风险保证金
	 */
	public BigDecimal getRiskAssuranceAmt() {
		return riskAssuranceAmt;
	}

	/**
	 * 设置 风险保证金 字段:hx_daily_data.risk_assurance_amt
	 *
	 * @param riskAssuranceAmt
	 *            the value for hx_daily_data.risk_assurance_amt, 风险保证金
	 */
	public void setRiskAssuranceAmt(BigDecimal riskAssuranceAmt) {
		this.riskAssuranceAmt = riskAssuranceAmt;
	}

	/**
	 * 获取 银行止付日期 字段:hx_daily_data.bank_stop_date
	 *
	 * @return hx_daily_data.bank_stop_date, 银行止付日期
	 */
	public Date getBankStopDate() {
		return bankStopDate;
	}

	/**
	 * 设置 银行止付日期 字段:hx_daily_data.bank_stop_date
	 *
	 * @param bankStopDate
	 *            the value for hx_daily_data.bank_stop_date, 银行止付日期
	 */
	public void setBankStopDate(Date bankStopDate) {
		this.bankStopDate = bankStopDate;
	}

	/**
	 * 获取 银行支付流水号 字段:hx_daily_data.bank_stop_flow
	 *
	 * @return hx_daily_data.bank_stop_flow, 银行支付流水号
	 */
	public String getBankStopFlow() {
		return bankStopFlow;
	}

	/**
	 * 设置 银行支付流水号 字段:hx_daily_data.bank_stop_flow
	 *
	 * @param bankStopFlow
	 *            the value for hx_daily_data.bank_stop_flow, 银行支付流水号
	 */
	public void setBankStopFlow(String bankStopFlow) {
		this.bankStopFlow = bankStopFlow == null ? null : bankStopFlow.trim();
	}

	/**
	 * 获取 状态 1-可用 0-不可用 字段:hx_daily_data.status
	 *
	 * @return hx_daily_data.status, 状态 1-可用 0-不可用
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置 状态 1-可用 0-不可用 字段:hx_daily_data.status
	 *
	 * @param status
	 *            the value for hx_daily_data.status, 状态 1-可用 0-不可用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取 创建时间 字段:hx_daily_data.create_time
	 *
	 * @return hx_daily_data.create_time, 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间 字段:hx_daily_data.create_time
	 *
	 * @param createTime
	 *            the value for hx_daily_data.create_time, 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * ,hx_daily_data
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", merchantId=").append(merchantId);
		sb.append(", checkDate=").append(checkDate);
		sb.append(", dealNum=").append(dealNum);
		sb.append(", tradeType=").append(tradeType);
		sb.append(", channelFlowFather=").append(channelFlowFather);
		sb.append(", channelFlowSon=").append(channelFlowSon);
		sb.append(", loanNo=").append(loanNo);
		sb.append(", paymentAcNo=").append(paymentAcNo);
		sb.append(", paymentAcName=").append(paymentAcName);
		sb.append(", paymentBankNo=").append(paymentBankNo);
		sb.append(", paymentBankName=").append(paymentBankName);
		sb.append(", receiptAcNo=").append(receiptAcNo);
		sb.append(", receiptAcName=").append(receiptAcName);
		sb.append(", receiptBankNo=").append(receiptBankNo);
		sb.append(", receiptBankName=").append(receiptBankName);
		sb.append(", amount=").append(amount);
		sb.append(", feeAmt=").append(feeAmt);
		sb.append(", acCustodyAmt=").append(acCustodyAmt);
		sb.append(", riskAssuranceAmt=").append(riskAssuranceAmt);
		sb.append(", bankStopDate=").append(bankStopDate);
		sb.append(", bankStopFlow=").append(bankStopFlow);
		sb.append(", status=").append(status);
		sb.append(", createTime=").append(createTime);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 字符串转成对账实体对象
	 * @author YJQ  2017年7月10日
	 * @param firStr
	 * @param fieldStr
	 * @return
	 * @throws Exception
	 */
	public HxDailyData parseRowData(String firStr, String fieldStr) throws Exception {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
		String[] firRow = firStr.split("\\|");
		String[] fieldArr = fieldStr.split("\\|");

		HxDailyData dailyData = new HxDailyData();
		
		if (firRow.length < 3) {
			throw new PageInfoException("对账汇总数据缺失", 1003);
		}
		
		dailyData.setMerchantId(firRow[0]);
		dailyData.setCheckDate(StringUtils.isEmpty(firRow[1]) ? null : dataFormat.parse(firRow[1]));
		dailyData.setDealNum(StringUtils.isEmpty(firRow[2]) ? null : Integer.parseInt(firRow[2]));

		if (fieldArr.length < 18) {
			throw new PageInfoException("对账明细数据缺失", 1003);
		}

		dailyData.setTradeType(StringUtils.isEmpty(fieldArr[0]) ? null : Integer.parseInt(fieldArr[0]));
		dailyData.setChannelFlowFather(fieldArr[1]);
		dailyData.setChannelFlowSon(fieldArr[2]);
		dailyData.setLoanNo(fieldArr[3]);
		dailyData.setPaymentAcNo(fieldArr[4]);
		dailyData.setPaymentAcName(fieldArr[5]);
		dailyData.setPaymentBankNo(fieldArr[6]);
		dailyData.setPaymentBankName(fieldArr[7]);
		dailyData.setReceiptAcNo(fieldArr[8]);
		dailyData.setReceiptAcName(fieldArr[9]);
		dailyData.setReceiptBankNo(fieldArr[10]);
		dailyData.setReceiptBankName(fieldArr[11]);
		dailyData.setAmount(StringUtils.isEmpty(fieldArr[12]) ? null : new BigDecimal(fieldArr[12]));
		dailyData.setFeeAmt(StringUtils.isEmpty(fieldArr[13]) ? null : new BigDecimal(fieldArr[13]));
		dailyData.setAcCustodyAmt(StringUtils.isEmpty(fieldArr[14]) ? null : new BigDecimal(fieldArr[14]));
		dailyData.setRiskAssuranceAmt(StringUtils.isEmpty(fieldArr[15]) ? null : new BigDecimal(fieldArr[15]));
		dailyData.setBankStopDate(StringUtils.isEmpty(fieldArr[16]) ? null : dataFormat.parse(fieldArr[16]));
		dailyData.setBankStopFlow(fieldArr[17]);
		dailyData.setCreateTime(new Date());
		return dailyData;
	}
}