package com.mrbt.lingmoney.model.trading;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * @author lihq
 * @date 2016年8月17日17:37:15
 * @version 1.0
 * @description 京东手机端快捷支付所需参数实体
 * @since
 **/
public class PaymentClient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1000415039341841718L;
	String uId;// 用户id
	String tId;// 交易id
	String infoId;// infoid
	String dizNumber;// 订单号
	String buyMoney;// 购买金额
	String productCode;// 产品code码
	String productName;// 产品名称
	long remainDt;// 剩余支付时间

	public long getRemainDt() {
		return remainDt;
	}

	public void setRemainDt(long remainDt) {
		this.remainDt = remainDt;
	}

	public PaymentClient() {
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getDizNumber() {
		return dizNumber;
	}

	public void setDizNumber(String dizNumber) {
		this.dizNumber = dizNumber;
	}

	public String getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(String buyMoney) {
		this.buyMoney = buyMoney;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		return jsonObject.fromObject(this).toString();
	}
}
