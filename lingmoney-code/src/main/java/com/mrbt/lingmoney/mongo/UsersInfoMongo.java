package com.mrbt.lingmoney.mongo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users_info")
public class UsersInfoMongo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9154697266026469590L;
	
	/**
	 * 数据ID
	 */
	private Object _id;
	
	/**
	 * 用户ID
	 */
	private String u_id;
	
	/**
	 * 昵称
	 */
	private String nick_name;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 注册日期
	 */
	private Date reg_date;

	/**
	 * 身份证
	 */
	private String id_card;

	/**
	 * 推荐码
	 */
	private String referral_code;

	/**
	 * 上级推荐码
	 */
	private String super_referral_code;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 实名认证
	 */
	private int certification;

	/**
	 * 绑卡
	 */
	private int bank;

	public Object get_id() {
		return _id;
	}

	public void set_id(Object _id) {
		this._id = _id;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
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

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getReferral_code() {
		return referral_code;
	}

	public void setReferral_code(String referral_code) {
		this.referral_code = referral_code;
	}

	public String getSuper_referral_code() {
		return super_referral_code;
	}

	public void setSuper_referral_code(String super_referral_code) {
		this.super_referral_code = super_referral_code;
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
