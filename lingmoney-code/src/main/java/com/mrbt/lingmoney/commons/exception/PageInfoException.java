package com.mrbt.lingmoney.commons.exception;

/**
 * 此自定义异常类可返回msg+code的异常
 * 
 * @author YJQ
 *
 */
public class PageInfoException extends Exception {
	private static final long serialVersionUID = 1L;
	private Integer code;

	public PageInfoException() {
		super();
	}

	public PageInfoException(String msg, Integer errorCode) {
		super(msg);
		code = errorCode;
	}

	public Integer getCode() {
		return code;
	}
}
