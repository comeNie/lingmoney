package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义活动bean
 * 
 * @author lihq
 * @date 2017年4月24日 上午10:10:15
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class ActivityProductCustomer extends ActivityProductWithBLOBs implements Serializable {

	private static final long serialVersionUID = 1L;

	private int buyState; // buyState代表活动状态，0表示活动中，1表示还未开始，2表示已结束

	private List<ProductCustomer> productList; // 产品列表

	private int sellCount; // 累计参加人数

	private String actTitleImage; // 标题图片

	private String actImage; // 活动图片

	private Float actRecommendations; // 推荐度 3，3.5，4，4.5，5
	private int newFlag; // 是否有new标志 0否 1是 （状态为活动中，并且开始时间距离当前时间小于等于3天）
	private String timeInterval; // 时间区间 普通活动：2017-05-15~2017-05-18
									// 早点8：2017-05-15 08:00~10:00

	private long distanceStartTime;// 距离活动开始时间
	private long distanceEndTime;// 距离活动结束时间
	private long currentDate;// 当前时间

	public int getBuyState() {
		return buyState;
	}

	public void setBuyState(int buyState) {
		this.buyState = buyState;
	}

	public List<ProductCustomer> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductCustomer> productList) {
		this.productList = productList;
	}

	public int getSellCount() {
		return sellCount;
	}

	public void setSellCount(int sellCount) {
		this.sellCount = sellCount;
	}

	public String getActTitleImage() {
		return actTitleImage;
	}

	public void setActTitleImage(String actTitleImage) {
		this.actTitleImage = actTitleImage;
	}

	public String getActImage() {
		return actImage;
	}

	public void setActImage(String actImage) {
		this.actImage = actImage;
	}

	public Float getActRecommendations() {
		return actRecommendations;
	}

	public void setActRecommendations(Float actRecommendations) {
		this.actRecommendations = actRecommendations;
	}

	public long getDistanceStartTime() {
		return distanceStartTime;
	}

	public void setDistanceStartTime(long distanceStartTime) {
		this.distanceStartTime = distanceStartTime;
	}

	public long getDistanceEndTime() {
		return distanceEndTime;
	}

	public void setDistanceEndTime(long distanceEndTime) {
		this.distanceEndTime = distanceEndTime;
	}

	public long getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(long currentDate) {
		this.currentDate = currentDate;
	}

	public int getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(int newFlag) {
		this.newFlag = newFlag;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

}