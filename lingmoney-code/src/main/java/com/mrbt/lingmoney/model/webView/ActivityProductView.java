package com.mrbt.lingmoney.model.webView;

import java.util.Date;
import java.util.List;

import com.mrbt.lingmoney.model.ActivityProduct;
import com.mrbt.lingmoney.model.Product;

/**
 *@author syb
 *@date 2017年5月16日 下午3:06:23
 *@version 1.0
 *@description 
 **/
public class ActivityProductView extends ActivityProduct{
	private static final long serialVersionUID = 4789582604223390491L;

	private int buyState; // buyState代表活动状态，0表示活动中，1表示还未开始，2表示已结束

	private Date nextBuyDate; // 下次购买日期

	private List<Product> productList;

	public int getBuyState() {
		return buyState;
	}

	public void setBuyState(int buyState) {
		this.buyState = buyState;
	}

	public Date getNextBuyDate() {
		return nextBuyDate;
	}

	public void setNextBuyDate(Date nextBuyDate) {
		this.nextBuyDate = nextBuyDate;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
}
