package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BuyRecordsVo implements Serializable {
	private static final long serialVersionUID = -7606362389103037378L;
	private Integer tId;// 交易ID
	private Integer pId;// 产品ID
	private Integer fTime;// 产品固定期限
	private BigDecimal fYield;// 产品收益率
	private Integer pStatus;// 产品状态
	private String pName;// 产品名称
	private String uId;// 用户ID
	private String uName;// 用户姓名
	private String telephone;// 用户手机号
	private String idCard;// 用户身份证号
	private BigDecimal financialMoney;// 理财金额
	private Date buyDt; // 购买时间
	private Integer tStatus;// 订单状态
	private BigDecimal interest;// 预期收益
	private Date minSellDt; // 到期时间
	private Date valueDt; // 计息日
	private Date eDdt; // 结束时间
	private String pCode;// 产品code

	public Date geteDdt() {
		return eDdt;
	}

	public void seteDdt(Date eDdt) {
		this.eDdt = eDdt;
	}

	public Date getValueDt() {
		return valueDt;
	}

	public void setValueDt(Date valueDt) {
		this.valueDt = valueDt;
	}

	public Integer gettId() {
		return tId;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Integer getfTime() {
		return fTime;
	}

	public void setfTime(Integer fTime) {
		this.fTime = fTime;
	}

	public BigDecimal getfYield() {
		return fYield;
	}

	public void setfYield(BigDecimal fYield) {
		this.fYield = fYield;
	}

	public Integer getpStatus() {
		return pStatus;
	}

	public void setpStatus(Integer pStatus) {
		this.pStatus = pStatus;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public BigDecimal getFinancialMoney() {
		return financialMoney;
	}

	public void setFinancialMoney(BigDecimal financialMoney) {
		this.financialMoney = financialMoney;
	}

	public Date getBuyDt() {
		return buyDt;
	}

	public void setBuyDt(Date buyDt) {
		this.buyDt = buyDt;
	}

	public Date getMinSellDt() {
		return minSellDt;
	}

	public void setMinSellDt(Date minSellDt) {
		this.minSellDt = minSellDt;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public Integer gettStatus() {
		return tStatus;
	}

	public void settStatus(Integer tStatus) {
		this.tStatus = tStatus;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

}
