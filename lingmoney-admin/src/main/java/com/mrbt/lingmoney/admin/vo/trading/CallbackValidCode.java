package com.mrbt.lingmoney.admin.vo.trading;

/**
 * 
 * @author Administrator
 *
 */
public enum CallbackValidCode {
	callback_success(30000, "验证正确"), callback_user_error(30010, "用户错误"), callback_info_status_error(
			31000, "交易详细信息表状态更新错误"), callback_trading_error(31001, "更新交易表错误"), callback_product_error(
			31002, "更新产品表错误"), callback_user_account_error(31003, "更新用户账户表错误"), callback_info_status_check(
			3104, "检测详细信息表是否已经更新完毕"), callback_server_error(39000, "回调服务器错误");
	private int code;
	private String msg;

	/**
	 * 
	 * @param code	code
	 * @param msg	msg
	 */
	CallbackValidCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
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
}
