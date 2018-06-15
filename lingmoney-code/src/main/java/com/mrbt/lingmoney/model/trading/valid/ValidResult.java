package com.mrbt.lingmoney.model.trading.valid;

public class ValidResult {
	public int code;
	public String msg;
	public boolean result;

	public ValidResult(int code, String msg, boolean result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
