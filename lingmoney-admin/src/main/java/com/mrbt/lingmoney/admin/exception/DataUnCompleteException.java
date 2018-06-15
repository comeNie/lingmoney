package com.mrbt.lingmoney.admin.exception;

/**
 * @author syb
 * @date 2017年8月31日 下午2:44:16
 * @version 1.0
 * @description 自定义异常，操作数据库失败时抛出，用于事物回滚
 **/
public class DataUnCompleteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2089230768764955191L;

	public DataUnCompleteException() {
	}

	public DataUnCompleteException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
	}

	public DataUnCompleteException(String message, Throwable cause) {
	}

	public DataUnCompleteException(String message) {
	}

	public DataUnCompleteException(Throwable cause) {
	}

}
