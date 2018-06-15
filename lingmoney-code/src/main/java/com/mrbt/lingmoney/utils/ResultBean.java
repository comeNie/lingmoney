package com.mrbt.lingmoney.utils;

import java.io.Serializable;

/**
 * @author luox
 * @Date 2017年4月24日
 */
public class ResultBean implements Serializable {
	private static final long serialVersionUID = -4053718473318374144L;
	private String result;

	public ResultBean() { }

	public ResultBean(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
