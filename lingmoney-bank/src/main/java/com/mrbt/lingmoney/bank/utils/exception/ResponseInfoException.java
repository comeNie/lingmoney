package com.mrbt.lingmoney.bank.utils.exception;

/**
 * 此自定义异常类可返回msg+code的异常 用于bank层向上抛出信息
 * 
 * @author YJQ
 *
 */
public class ResponseInfoException extends Exception {
	private static final long serialVersionUID = 1L;
	private Integer code;

	public ResponseInfoException() {
		super();
	}

	public ResponseInfoException(String msg, Integer errorCode) {
		super(msg);
		code = errorCode;
	}

	public Integer getCode() {
		return code;
	}
}
