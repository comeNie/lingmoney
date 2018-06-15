package com.mrbt.lingmoney.model.schedule;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author syb
 * @date 2017年6月29日 上午10:32:00
 * @version 1.0
 * @description 稳赢宝赎回数据
 **/
public class WenYingBaoRedeem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 卖出总金额
	 */
	private BigDecimal sellCount;
	/**
	 * 交易id
	 */
	private int tId;
	/**
	 * 购买总金额
	 */
	private BigDecimal buyMonney;
	/**
	 * 用户ID
	 */
	private String uId;
	/**
	 * 产品类型 0 领钱儿 1中粮 2华兴银行
	 */
	private int pType;
	/**
	 * 产品code
	 */
	private String pCode;
	/**
	 * 用户账户id
	 */
	private int aId;

	/**
	 * 平台佣金
	 */
	private BigDecimal costValue;

	public BigDecimal getSellCount() {
		return sellCount;
	}

	public void setSellCount(BigDecimal sellCount) {
		this.sellCount = sellCount;
	}

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	public BigDecimal getBuyMonney() {
		return buyMonney;
	}

	public void setBuyMonney(BigDecimal buyMonney) {
		this.buyMonney = buyMonney;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public int getpType() {
		return pType;
	}

	public void setpType(int pType) {
		this.pType = pType;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	public BigDecimal getCostValue() {
		return costValue;
	}

	public void setCostValue(BigDecimal costValue) {
		this.costValue = costValue;
	}

	@Override
	public String toString() {
		return "WenYingBaoRedeem [sellCount=" + sellCount + ", tId=" + tId + ", buyMonney=" + buyMonney + ", uId="
				+ uId + ", pType=" + pType + ", pCode=" + pCode + ", aId=" + aId + ", costValue=" + costValue + "]";
	}


}
