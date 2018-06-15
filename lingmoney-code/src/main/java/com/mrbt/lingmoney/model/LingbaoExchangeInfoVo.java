package com.mrbt.lingmoney.model;

import java.util.Date;

public class LingbaoExchangeInfoVo {

	private Integer id;

	private String uId;
	// 用户姓名
	private String uname;

	// 用户手机号
	private String uPhoneNumber;

	private Integer giftId;
	// 礼品名称
	private String giftName;
	// 礼品类型 0实物礼品 1虚拟礼品
	private Integer gifttype;
	// 礼品领取方式：0寄送，1自取，2无需领取
	private Integer receiveWay;

	private Integer num;

	private Integer integral;

	private Integer activityId;
	// 活动名称
	private String activityName;

	private Integer addressId;

	private String serialNumber;

	private String expressNumber;

	private Integer status;

	private Date exchangeTime;

	private Date sendTime;

	private Date receiveTime;

	private Integer type;

	private String expressCompany;

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getuPhoneNumber() {
		return uPhoneNumber;
	}

	public void setuPhoneNumber(String uPhoneNumber) {
		this.uPhoneNumber = uPhoneNumber;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getGifttype() {
		return gifttype;
	}

	public void setGifttype(Integer gifttype) {
		this.gifttype = gifttype;
	}

	public Integer getReceiveWay() {
		return receiveWay;
	}

	public void setReceiveWay(Integer receiveWay) {
		this.receiveWay = receiveWay;
	}

}
