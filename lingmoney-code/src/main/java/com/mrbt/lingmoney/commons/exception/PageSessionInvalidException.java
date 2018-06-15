package com.mrbt.lingmoney.commons.exception;

/**
 * 页面级别的session过期
 * 
 * @author lzp
 *
 */
public class PageSessionInvalidException extends MyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static final String ERR_CODE = "700";

	public PageSessionInvalidException(String message) {
		super(message);
	}

}
