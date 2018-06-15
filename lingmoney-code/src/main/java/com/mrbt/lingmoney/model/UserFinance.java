package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 罗鑫
 * @Date 2017年4月17日
 */
public class UserFinance implements Serializable {
	private static final long serialVersionUID = 3531095080865275700L;

	private Integer id;// 交易ID
	private String productCode;// 产品Code
	private String productName;// 产品名称
	private Date stDt;// 产品开始时间
	private Date edDt;// 产品结束时间
	private Integer productTerm;// 固定期限
	private BigDecimal financialMoney;// 理财金额，最终金额，计算利息
	private BigDecimal payMoney;// 支付金额，扣除红包加息券等的金额
	private Date valueDt;// 计息日
	private Date buyDt;// 购买时间
	private Date expireDt;// 到期时间（预期）
	private String proType;// 产品类型（车贷宝、稳盈宝）
	private String reWay;// 赎回方式
	private Integer status;// 产品状态
	private Long remainDt;// 剩余支付时间（毫秒）
	private BigDecimal fYield;// 预期年化
	private BigDecimal expectEarn;// 预期收益
	private Integer hrpType;// 1:加息券，2：红包
	private Double hrpNumber;// 赠送金额或加息比例
	private Double actualAmount;// 实际赠送金额，红包的时候为0,
	private Integer holdDays;// 持有天数
	private BigDecimal difference; // 距离下一档位金额
	private String stalls;// 随心取现在档位
	private String nextStalls;// 随心取下一档
	private BigDecimal allInterest;// 随心取累计总收益
	private int upDay; // 距离升级利率天数
	private BigDecimal upDayRate; // 下一天数利率
	private Integer pType;//绑卡类型
	private Integer pcId;//产品分类Id
	private Integer insuranceTrust;
	private BigDecimal addYield; // 加息率
	private Integer isDebt; // 是否可债权转让 0：不可转让 1：可转让
	 

	public Integer getPcId() {
		return pcId;
	}

	public void setPcId(Integer pcId) {
		this.pcId = pcId;
	}

	public Integer getpType() {
		return pType;
	}

	public void setpType(Integer pType) {
		this.pType = pType;
	}

	public Integer getHrpType() {
		return hrpType;
	}

	public void setHrpType(Integer hrpType) {
		this.hrpType = hrpType;
	}

	public Double getHrpNumber() {
		return hrpNumber;
	}

	public void setHrpNumber(Double hrpNumber) {
		this.hrpNumber = hrpNumber;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Date getStDt() {
		return stDt;
	}

	public void setStDt(Date stDt) {
		this.stDt = stDt;
	}

	public Date getEdDt() {
		return edDt;
	}

	public void setEdDt(Date edDt) {
		this.edDt = edDt;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getExpireDt() {
		return expireDt;
	}

	public void setExpireDt(Date expireDt) {
		this.expireDt = expireDt;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public Date getBuyDt() {
		return buyDt;
	}

	public void setBuyDt(Date buyDt) {
		this.buyDt = buyDt;
	}

	public Long getRemainDt() {
		return remainDt;
	}

	public void setRemainDt(Long remainDt) {
		this.remainDt = remainDt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public void setValueDt(Date valueDt) {
		this.valueDt = valueDt;
	}

	// public Integer getHoldDays() {
	// if (valueDt != null) {
	// long currentTimeMillis = System.currentTimeMillis();
	// long d = currentTimeMillis - valueDt.getTime();
	// return (int) (d / 1000 / 3600 / 24);
	// }
	// return null;
	// }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getFinancialMoney() {
		return financialMoney;
	}

	public void setFinancialMoney(BigDecimal financialMoney) {
		this.financialMoney = financialMoney;
	}

	public BigDecimal getfYield() {
		return fYield;
	}

	public void setfYield(BigDecimal fYield) {
		this.fYield = fYield;
	}

	public BigDecimal getExpectEarn() {
		return expectEarn;
	}

	public void setExpectEarn(BigDecimal expectEarn) {
		this.expectEarn = expectEarn;
	}

	public Integer getProductTerm() {
		return productTerm;
	}

	public void setProductTerm(Integer productTerm) {
		this.productTerm = productTerm;
	}

	public Integer getHoldDays() {
		return holdDays;
	}

	public void setHoldDays(Integer holdDays) {
		this.holdDays = holdDays;
	}

	public Date getValueDt() {
		return valueDt;
	}

	public String getReWay() {
		return reWay;
	}

	public void setReWay(String reWay) {
		this.reWay = reWay;
	}

	public BigDecimal getDifference() {
		return difference;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}

	public String getStalls() {
		return stalls;
	}

	public void setStalls(String stalls) {
		this.stalls = stalls;
	}

	public String getNextStalls() {
		return nextStalls;
	}

	public void setNextStalls(String nextStalls) {
		this.nextStalls = nextStalls;
	}

	public BigDecimal getAllInterest() {
		return allInterest;
	}

	public void setAllInterest(BigDecimal allInterest) {
		this.allInterest = allInterest;
	}

	public int getUpDay() {
		return upDay;
	}

	public void setUpDay(int upDay) {
		this.upDay = upDay;
	}

	public BigDecimal getUpDayRate() {
		return upDayRate;
	}

	public void setUpDayRate(BigDecimal upDayRate) {
		this.upDayRate = upDayRate;
	}

	public Integer getInsuranceTrust() {
		return insuranceTrust;
	}

	public void setInsuranceTrust(Integer insuranceTrust) {
		this.insuranceTrust = insuranceTrust;
	}

	public BigDecimal getAddYield() {
		return addYield;
	}

	public void setAddYield(BigDecimal addYield) {
		this.addYield = addYield;
	}

	public Integer getIsDebt() {
		return isDebt;
	}

	public void setIsDebt(Integer isDebt) {
		this.isDebt = isDebt;
	}

}
