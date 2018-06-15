package com.mrbt.lingmoney.model.schedule;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 卖出产品的trading
 * 
 * @author Administrator
 *
 */
public class SellTrading {
	
	/**
	 * trading.status
	 */
	private Integer status;
	/**
	 * trading.u_id
	 */
	private String uid;
	/**
	 * trading.sellDt
	 */
	private Date sellDt;
	/**
	 * trading.id
	 */
	private Integer tid;
	/**
	 * trading.pCode
	 */
	private String pCode;
	/**
	 * trading.pId
	 */
	private Integer pId;
	/**
	 * trading.financialMoney
	 */
	private BigDecimal financialMoney;
	/**
	 * trading.minSellDt
	 */
	private Date minSellDt;
	/**
	 * trading.sell_money
	 */
	private BigDecimal sellMoney;
	/**
	 * trading.biz_code
	 */
	private String bizCode;
	/**
	 * trading.out_biz_code
	 */
	private String outBizCode;
	
	/**
	 * traidng_fix_info.id
	 */
	private Integer infoId;
	/**
	 * trading_fix_info.expire_dt
	 */
	private Date expireDt;
	/**
	 * trading_fix_info.interest_rate
	 */
	private BigDecimal interestRate;
	
	/**
	 * product.rule
	 */
	private Integer rule;
	/**
	 * product.fYield
	 */
	private BigDecimal fYield;

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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getSellDt() {
		return sellDt;
	}

	public void setSellDt(Date sellDt) {
		this.sellDt = sellDt;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public BigDecimal getFinancialMoney() {
		return financialMoney;
	}

	public void setFinancialMoney(BigDecimal financialMoney) {
		this.financialMoney = financialMoney;
	}

	public Date getMinSellDt() {
		return minSellDt;
	}

	public void setMinSellDt(Date minSellDt) {
		this.minSellDt = minSellDt;
	}

	public Date getExpireDt() {
		return expireDt;
	}

	public void setExpireDt(Date expireDt) {
		this.expireDt = expireDt;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getSellMoney() {
		return sellMoney;
	}

	public void setSellMoney(BigDecimal sellMoney) {
		this.sellMoney = sellMoney;
	}

	public Integer getRule() {
		return rule;
	}

	public void setRule(Integer rule) {
		this.rule = rule;
	}

	public BigDecimal getfYield() {
		return fYield;
	}

	public void setfYield(BigDecimal fYield) {
		this.fYield = fYield;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getOutBizCode() {
		return outBizCode;
	}

	public void setOutBizCode(String outBizCode) {
		this.outBizCode = outBizCode;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	@Override
	public String toString() {
		return "SellTrading [status=" + status + ", uid=" + uid + ", sellDt=" + sellDt + ", tid=" + tid + ", pCode="
				+ pCode + ", pId=" + pId + ", financialMoney=" + financialMoney + ", minSellDt=" + minSellDt
				+ ", sellMoney=" + sellMoney + ", bizCode=" + bizCode + ", outBizCode=" + outBizCode + ", infoId="
				+ infoId + ", expireDt=" + expireDt + ", interestRate=" + interestRate + ", rule=" + rule + ", fYield="
				+ fYield + "]";
	}

}
