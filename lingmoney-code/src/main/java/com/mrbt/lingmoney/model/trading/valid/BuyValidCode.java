package com.mrbt.lingmoney.model.trading.valid;

public enum BuyValidCode {
	buy_success(10000, "验证正确"), rule_money_error(10001, "购买金额超出募集金额"), rule_dt_error(
			10002, "购买时间超出募集时间"), rule_status_error(10003, "项目已经运行"), less_min_money(
			10011, "购买金额小于起投金额"), less_increa_money(10012, "购买金额非递增金额整数倍"), product_money_is_zero(
			10013, "产品金额错误"), product_money_big(10014, "投资金额大于体验金额"), product_update_error(
			10021, "产品更新错误,以筹金额大于筹备金额"), buy_server_error(10031,
			"服务器内部错误，请重新操作"), buy_cost_error(10041, "收取佣金错误"), buy_float_info_error(
			10051, "购买浮动类产品明细错误"), buy_fix_info_error(10052, "购买固定类产品明细错误"), buy_type_float_error(
			11001, "产品类型错误，不是浮动类产品"), buy_type_fix_error(11002,
			"产品类型错误，不是固定类产品"), buy_type_error(11003, "产品类型错误,处理不了该产品分类"), buy_fix_invest_error(
			12000, "定投错误，找不到已定投的产品"), buy_fix_invest_update_error(12001,
			"定投错误，购买定投产品错误");
	private int code;
	private String msg;

	private BuyValidCode(int code, String msg) {
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
