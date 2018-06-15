package com.mrbt.lingmoney.model.trading.valid;

public enum SellValidCode {
	sell_success(20000, "验证正确"), sell_dt_less_value(20001, "赎回日期小于计息日期"), sell_dt_less_min(
			20002, "赎回日期小于可赎回日期"), sell_dt_error(20003, "赎回日期错误"), sell_user_error(
			20011, "赎回用户错误，非本用户"), sell_trading_error(20012, "交易不存在"), sell_trading_info_error(
			20013, "交易详细信息不存在"), sell_dt_not_trade(20021, "不是可以交易的日期"), sell_value_none(
			20031, "当前没有卖出的净值(仅测试用)"), sell_buy_value_none(20032, "当前没有买入的净值"), sell_buy_status_error(
			20032, "不是已经买入的产品"), sell_server_error(21000, "服务器错误"), sell_server_trading_error(
			21001, "交易表更新错误"), sell_server_cost_error(21002, "佣金收取失败"), sell_server_interest_error(
			21003, "利息计算失败，请联系客服人员"), sell_server_wallet_error(21004,
			"钱包模式产品卖出失败"),sell_server_takeheart_error(23001,"随心取模式产品卖出失败"),sell_server_takehread_interest_error(
				23002, "随心取模式利息计算失败，请联系客服人员"), sell_bigsell_wallet_error(22001, "触发大额赎回");
	private int code;
	private String msg;

	private SellValidCode(int code, String msg) {
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
