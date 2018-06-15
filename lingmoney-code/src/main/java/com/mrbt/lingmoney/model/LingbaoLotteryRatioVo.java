package com.mrbt.lingmoney.model;

public class LingbaoLotteryRatioVo extends LingbaoLotteryRatio {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeName;// 活动名称
	private String giftName;// 礼品名称

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
}