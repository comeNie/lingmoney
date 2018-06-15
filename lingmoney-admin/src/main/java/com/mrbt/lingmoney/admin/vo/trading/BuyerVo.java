package com.mrbt.lingmoney.admin.vo.trading;

import java.util.Date;

/**
 * 
 *
 * @author lihq
 * @date 2017年7月26日 下午5:10:29
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class BuyerVo {
	private Integer id;
	private String userName;
	private String productName;
	private Date buyTime;
	private Integer uId;
	private Double financialMoney;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return	uId
	 */	
	public Integer getuId() {
		return uId;
	}

	/**
	 * 
	 * @param uId	uId
	 */
	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public Double getFinancialMoney() {
		return financialMoney;
	}

	public void setFinancialMoney(Double financialMoney) {
		this.financialMoney = financialMoney;
	}

}
