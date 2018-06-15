package com.mrbt.lingmoney.model.admin;

import com.mrbt.lingmoney.model.GiftDetail;

public class GiftDetailVo extends GiftDetail {

	private static final long serialVersionUID = 3829906936612831106L;

	private String uTel;// 用户手机号
	private String uName;// 用户姓名

	public String getuTel() {
		return uTel;
	}

	public void setuTel(String uTel) {
		this.uTel = uTel;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

}