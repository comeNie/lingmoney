package com.mrbt.lingmoney.model.schedule;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author syb
 * @date 2017年6月29日 上午10:57:24
 * @version 1.0
 * @description 随心取赎回数据
 **/
public class TakeHeartRedeem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	/**
	 * 用户id
	 */
	private String uId;
	/**
	 * 卖出金额
	 */
	private BigDecimal sellMoney;
	/**
	 * 序列号 takeheart_transaction_flow.number
	 */
	private String number;
	/**
	 * 赎回类型：0普通赎回,1全部赎回
	 */
	private int redeemType;
	/**
	 * 交易信息id
	 */
	private int tfId;
	/**
	 * 兑付卡号
	 */
	private String cardnumber;
	/**
	 * 产品code
	 */
	private String pCode;
	/**
	 * 交易id
	 */
	private int tId;
	/**
	 * 用户账户id
	 */
	private int aId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public BigDecimal getSellMoney() {
		return sellMoney;
	}

	public void setSellMoney(BigDecimal sellMoney) {
		this.sellMoney = sellMoney;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getRedeemType() {
		return redeemType;
	}

	public void setRedeemType(int redeemType) {
		this.redeemType = redeemType;
	}

	public int getTfId() {
		return tfId;
	}

	public void setTfId(int tfId) {
		this.tfId = tfId;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	@Override
	public String toString() {
		return "TakeHeartRedeem [id=" + id + ", uId=" + uId + ", sellMoney=" + sellMoney + ", number=" + number
				+ ", redeemType=" + redeemType + ", tfId=" + tfId + ", cardnumber=" + cardnumber + ", pCode=" + pCode
				+ ", tId=" + tId + ", aId=" + aId + "]";
	}


}
