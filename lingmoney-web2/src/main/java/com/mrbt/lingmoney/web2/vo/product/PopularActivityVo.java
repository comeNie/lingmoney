package com.mrbt.lingmoney.web2.vo.product;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * 热门活动vo
 * @author Administrator
 *
 */
@Component
public class PopularActivityVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5918195610975022623L;
	
	/**
	 * ID
	 */
	private int id;
	
	/**
	 * 标题
	 */
	private String actTitle;
	
	/**
	 * 活动图片
	 */
	private String actTitleImage;
	
	/**
	 * 时间区间 普通活动：2017-05-15~2017-05-18
	 */
	private String timeInterval;
	
	/**
	 * 活动状态，0表示活动中，1表示还未开始，2表示已结束
	 */
	private int buyState;
	
	/**
	 * 推荐度 3，3.5，4，4.5，5
	 */
	private Float actRecommendations;
	
	/**
	 * 是否有new标志 0否 1是 （状态为活动中，并且开始时间距离当前时间小于等于3天）
	 */
	private int newFlag;
	
	/**
	 * 详情URL
	 */
	private String actUrl;
	
	/**
	 * 距离活动开始时间
	 */
	private long distanceStartTime;
	
	/**
	 * 距离活动结束时间
	 */
	private long distanceEndTime; 
	
	/**
	 * 当前时间
	 */
	private long currentDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActTitle() {
		return actTitle;
	}

	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
	}

	public String getActTitleImage() {
		return actTitleImage;
	}

	public void setActTitleImage(String actTitleImage) {
		this.actTitleImage = actTitleImage;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public int getBuyState() {
		return buyState;
	}

	public void setBuyState(int buyState) {
		this.buyState = buyState;
	}

	public Float getActRecommendations() {
		return actRecommendations;
	}

	public void setActRecommendations(Float actRecommendations) {
		this.actRecommendations = actRecommendations;
	}

	public int getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(int newFlag) {
		this.newFlag = newFlag;
	}

	public String getActUrl() {
		return actUrl;
	}

	public void setActUrl(String actUrl) {
		this.actUrl = actUrl;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
