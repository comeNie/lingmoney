package com.mrbt.lingmoney.model;

import java.math.BigDecimal;

/**
 * HxBidding/HxBorrowerInfo/HxBorrower 连表查询model
 * @author YJQ
 *
 */
public class HxBidBorrowUnionInfo {
	 /**
     * 主键
     * 表字段 : hx_bidding.id
     */
    private String id;

    /**
     * 关联产品id
     * 表字段 : hx_bidding.p_id
     */
    private Integer pId;

    /**
     * 应用标识 PC APP WX
     * 表字段 : hx_bidding.app_id
     */
    private String appId;

    /**
     * 借款编号
     * 表字段 : hx_bidding.loan_no
     */
    private String loanNo;

    /**
     * 标的编号，目前与借款编号相同
     * 表字段 : hx_bidding.invest_id
     */
    private String investId;

    /**
     * 标的名称
     * 表字段 : hx_bidding.invest_obj_name
     */
    private String investObjName;  // 取自产品表 name

    /**
     * 标的简介
     * 表字段 : hx_bidding.invest_obj_info
     */
    private String investObjInfo;

    /**
     * 最低投标金额
     * 表字段 : hx_bidding.min_invest_amt
     */
    private BigDecimal minInvestAmt; // 取自产品表 min_money

    /**
     * 最高投标金额
     * 表字段 : hx_bidding.max_invest_amt
     */
    private BigDecimal maxInvestAmt; // 取自产品表 buy_limit

    /**
     * 总标的金额，各个借款人列表中的BWAMT总和
     * 表字段 : hx_bidding.invest_obj_amt
     */
    private BigDecimal investObjAmt; // 取自产品表 prior_money

    /**
     * 招标开始日期，YYYYMMDD
     * 表字段 : hx_bidding.invest_begin_date
     */
    private String investBeginDate; // 取自产品表 st_dt

    /**
     * 招标到期日期，YYYYMMDD
     * 表字段 : hx_bidding.invest_end_date
     */
    private String investEndDate; // 取自产品表 ed_dt

    /**
     * 还款日期，YYYYMMDD
     * 表字段 : hx_bidding.repay_date
     */
    private String repayDate;

    /**
     * 0<年利率<=100 nnn.nnnnnn整数部分最多3位，小数部分最多 6位
     * 表字段 : hx_bidding.year_rate
     */
    private BigDecimal yearRate; // 取自产品表f_yieid

    /**
     * 期限，整型，天数，单位为天
     * 表字段 : hx_bidding.invest_range
     */
    private Integer investRange;// 取自产品表 f_time

    /**
     * 计息方式
     * 表字段 : hx_bidding.rate_stype
     */
    private String rateStype;

    /**
     * 还款方式
     * 表字段 : hx_bidding.repay_stype
     */
    private String repayStype; // 取自产品表 re_way

    /**
     * 标的状态，0 正常，1 撤销 ,  2 流标 ， 3 银行主动流标
     * 表字段 : hx_bidding.invest_obj_state
     */
    private String investObjState;

    /**
     * 借款人总数
     * 表字段 : hx_bidding.bw_total_num
     */
    private Integer bwTotalNum;

    /**
     * 备注
     * 表字段 : hx_bidding.remark
     */
    private String remark;

    /**
     * 是否为债权转让标的，0 否，1 是
     * 表字段 : hx_bidding.zr_flag
     */
    private String zrFlag;

    /**
     * 债权转让原标的，当ZRFLAG=1时必填
     * 表字段 : hx_bidding.ref_loan_no
     */
    private String refLoanNo;

    /**
     * 原投标第三方交易流水号，当ZRFLAG=1时必填
     * 表字段 : hx_bidding.old_reqseq
     */
    private String oldReqseq;
    
    
    /**
     * 借款信息id
     * 表字段 : hx_borrower_info.id
     */
    private String bwId;
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

    /**
     * 借款人抵押品简单描述
     * 表字段 : hx_borrower_info.mortgage_info
     */
    private String mortgageInfo;

    /**
     * 借款人审批通过日期，YYYYMMDD
     * 表字段 : hx_borrower_info.check_date
     */
    private String checkDate;

    /**
     * 备注（其它未尽事宜）
     * 表字段 : hx_borrower_info.remark
     */
    private String bwRemark;
    
    /**
     * 借款人姓名
     * 表字段 : hx_borrower.bw_acname
     */
    private String bwAcname;

    /**
     * 借款人证件类型，身份证：1010
     * 表字段 : hx_borrower.bw_idtype
     */
    private String bwIdtype;

    /**
     * 借款人证件号码，18位身份证
     * 表字段 : hx_borrower.bw_idno
     */
    private String bwIdno;

    /**
     * 借款人账号
     * 表字段 : hx_borrower.bw_acno
     */
    private String bwAcno;

    /**
     * 借款人账号所属行号，12位联行号，12位数字
     * 表字段 : hx_borrower.bw_acbankid
     */
    private String bwAcbankid;

    /**
     * 借款人账号所属行名
     * 表字段 : hx_borrower.bw_acbankname
     */
    private String bwAcbankname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	public String getInvestObjName() {
		return investObjName;
	}

	public void setInvestObjName(String investObjName) {
		this.investObjName = investObjName;
	}

	public String getInvestObjInfo() {
		return investObjInfo;
	}

	public void setInvestObjInfo(String investObjInfo) {
		this.investObjInfo = investObjInfo;
	}

	public BigDecimal getMinInvestAmt() {
		return minInvestAmt;
	}

	public void setMinInvestAmt(BigDecimal minInvestAmt) {
		this.minInvestAmt = minInvestAmt;
	}

	public BigDecimal getMaxInvestAmt() {
		return maxInvestAmt;
	}

	public void setMaxInvestAmt(BigDecimal maxInvestAmt) {
		this.maxInvestAmt = maxInvestAmt;
	}

	public BigDecimal getInvestObjAmt() {
		return investObjAmt;
	}

	public void setInvestObjAmt(BigDecimal investObjAmt) {
		this.investObjAmt = investObjAmt;
	}

	public String getInvestBeginDate() {
		return investBeginDate;
	}

	public void setInvestBeginDate(String investBeginDate) {
		this.investBeginDate = investBeginDate;
	}

	public String getInvestEndDate() {
		return investEndDate;
	}

	public void setInvestEndDate(String investEndDate) {
		this.investEndDate = investEndDate;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public BigDecimal getYearRate() {
		return yearRate;
	}

	public void setYearRate(BigDecimal yearRate) {
		this.yearRate = yearRate;
	}

	public Integer getInvestRange() {
		return investRange;
	}

	public void setInvestRange(Integer investRange) {
		this.investRange = investRange;
	}

	public String getRateStype() {
		return rateStype;
	}

	public void setRateStype(String rateStype) {
		this.rateStype = rateStype;
	}

	public String getRepayStype() {
		return repayStype;
	}

	public void setRepayStype(String repayStype) {
		this.repayStype = repayStype;
	}

	public String getInvestObjState() {
		return investObjState;
	}

	public void setInvestObjState(String investObjState) {
		this.investObjState = investObjState;
	}

	public Integer getBwTotalNum() {
		return bwTotalNum;
	}

	public void setBwTotalNum(Integer bwTotalNum) {
		this.bwTotalNum = bwTotalNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getZrFlag() {
		return zrFlag;
	}

	public void setZrFlag(String zrFlag) {
		this.zrFlag = zrFlag;
	}

	public String getRefLoanNo() {
		return refLoanNo;
	}

	public void setRefLoanNo(String refLoanNo) {
		this.refLoanNo = refLoanNo;
	}

	public String getOldReqseq() {
		return oldReqseq;
	}

	public void setOldReqseq(String oldReqseq) {
		this.oldReqseq = oldReqseq;
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

	public String getBwRemark() {
		return bwRemark;
	}

	public void setBwRemark(String bwRemark) {
		this.bwRemark = bwRemark;
	}

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

	public String getBwId() {
		return bwId;
	}

	public void setBwId(String bwId) {
		this.bwId = bwId;
	}
    
    
    
}
