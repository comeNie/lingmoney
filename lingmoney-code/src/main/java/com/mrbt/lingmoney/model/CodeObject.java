package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.util.Date;

public class CodeObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Date createDdate;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateDdate() {
		return createDdate;
	}

	public void setCreateDdate(Date createDdate) {
		this.createDdate = createDdate;
	}

}
