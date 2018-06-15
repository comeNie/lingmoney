package com.mrbt.lingmoney.model.trading;

public class SmsMessage implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8987087420820604253L;

	private String telephone;
	
	private String content;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
