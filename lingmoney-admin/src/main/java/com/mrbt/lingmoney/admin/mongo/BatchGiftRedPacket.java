package com.mrbt.lingmoney.admin.mongo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月7日
 */
public class BatchGiftRedPacket {

	private String uid;
	private String name;
	private String telephone;
	private String channelName;
	private String productName;
	private String buyDate;
	private String buyMoney;
	private String minSellDt;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public String getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(String buyMoney) {
		this.buyMoney = buyMoney;
	}

	public String getMinSellDt() {
		return minSellDt;
	}

	public void setMinSellDt(String minSellDt) {
		this.minSellDt = minSellDt;
	}

}
