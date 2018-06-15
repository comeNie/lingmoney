package com.mrbt.lingmoney.admin.exception;

/**
 * 页面级别的session过期
 * 
 * @author lzp
 *
 */
public class PageSessionInvalidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PageSessionInvalidException(String message) {
		super(message);
	}

}
