package com.mrbt.lingmoney.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 拉新活动奖品兑换信息值对象
 * 
 * @author Administrator
 * @date 2017年4月10日 下午3:12:04
 * @description
 * @since
 */
public class GiftExchangeInfoVo extends GiftExchangeInfo {
	private String telephone;// 推荐人手机号
	private String name;// 推荐人姓名
	private String referralCode;// 推荐人推荐码
	private String province;// 推荐人所在省
	private String city;// 推荐人所在市
	private String town;// 推荐人所在区/县
	private String address;// 推荐人详细地址
	private String employeeName;// 上级推荐人姓名
	private String employeeID;// 上级推荐人员工号
	private String department;// 上级推荐人部门
	private String employeeNameUp;// 上上级推荐人姓名
	private String employeeIDUp;// 上上级推荐人员工号
	private String departmentUp;// 上上级推荐人部门
	private String newName;// 新客户姓名
	private String newTelephone;// 新客户手机号
	private Date newRegDate;// 新客户注册日期
	private String newIdCard;// 新客户身份证号
	private Integer newAge;// 新客户年龄
	private String activityName;// 活动名称
	private BigDecimal threeBuyMoney;// 新客户购买3个月以上产品总金额

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewTelephone() {
		return newTelephone;
	}

	public void setNewTelephone(String newTelephone) {
		this.newTelephone = newTelephone;
	}

	public Date getNewRegDate() {
		return newRegDate;
	}

	public void setNewRegDate(Date newRegDate) {
		this.newRegDate = newRegDate;
	}

	public String getNewIdCard() {
		return newIdCard;
	}

	public void setNewIdCard(String newIdCard) {
		this.newIdCard = newIdCard;
	}

	public Integer getNewAge() {
		return newAge;
	}

	public void setNewAge(Integer newAge) {
		this.newAge = newAge;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getEmployeeNameUp() {
		return employeeNameUp;
	}

	public void setEmployeeNameUp(String employeeNameUp) {
		this.employeeNameUp = employeeNameUp;
	}

	public String getEmployeeIDUp() {
		return employeeIDUp;
	}

	public void setEmployeeIDUp(String employeeIDUp) {
		this.employeeIDUp = employeeIDUp;
	}

	public String getDepartmentUp() {
		return departmentUp;
	}

	public void setDepartmentUp(String departmentUp) {
		this.departmentUp = departmentUp;
	}

	public BigDecimal getThreeBuyMoney() {
		return threeBuyMoney;
	}

	public void setThreeBuyMoney(BigDecimal threeBuyMoney) {
		this.threeBuyMoney = threeBuyMoney;
	}

}