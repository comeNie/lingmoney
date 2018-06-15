package com.mrbt.lingmoney.admin.exception;

/**
 * @author luox
 * @Date 2017年5月25日
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -388928326534118474L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}
}
