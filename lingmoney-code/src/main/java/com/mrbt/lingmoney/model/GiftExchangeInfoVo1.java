package com.mrbt.lingmoney.model;

import java.math.BigDecimal;

/**
 * 拉新活动第二季 自定义vo
 * 
 * @author lihq
 * @date 2017年5月27日 下午4:47:25
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class GiftExchangeInfoVo1 {

	// referralId, SUM(buyMoney) buyMoney, uId, nickName, referralCode
	private String referralId;// 推荐人id，当前登录用户（内部员工会关联员工号）
	private BigDecimal buyMoney;// 新客户购买3个月以上产品总金额
	private String uId;// 推荐的用户id
	private String nickName;// 推荐的用户昵称
	private String referralCode;// 推荐的用户推荐码

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	public BigDecimal getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(BigDecimal buyMoney) {
		this.buyMoney = buyMoney;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

}