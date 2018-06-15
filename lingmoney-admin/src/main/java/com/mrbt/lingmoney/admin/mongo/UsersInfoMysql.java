package com.mrbt.lingmoney.admin.mongo;

import java.io.Serializable;
import java.util.Date;

public class UsersInfoMysql implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9154697266026469590L;
	
	private Object id;
	
	private String nickName;
	
	private String name;
	
	private String sex;
	
	private Date reg_date;
	
	private String idCard;
	
	private String referralCode;

	private String referralId;
	
	private String telephone;
	
	private int certification;
	
	private int bank;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getCertification() {
		return certification;
	}

	public void setCertification(int certification) {
		this.certification = certification;
	}

	public int getBank() {
		return bank;
	}

	public void setBank(int bank) {
		this.bank = bank;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
