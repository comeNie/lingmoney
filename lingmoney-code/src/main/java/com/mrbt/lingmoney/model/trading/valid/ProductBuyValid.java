package com.mrbt.lingmoney.model.trading.valid;


/**
 * 购买的验证
 * 
 * @author lzp
 *
 */

public class ProductBuyValid {

	public ProductBuyValid() {
	}

	// /**
	// * 按照购买的期限
	// *
	// * @return
	// */
	// private BuyValidCode productEndDt(Date dt) {
	// return (pcv.getEdDt() != null ? dt.before(pcv.getEdDt()) : true)
	// && (pcv.getStDt() != null ? pcv.getStDt().before(dt) : true) ?
	// BuyValidCode.buy_success
	// : BuyValidCode.rule_dt_error;
	// }

	// /**
	// * 按照购买的金额
	// *
	// * @return
	// */
	// private BuyValidCode productEndMoney(BigDecimal buyMoney) {
	// return BigDecimalUtils.lessAndEqu(buyMoney.add(pcv.getReachMoney()),
	// pcv.getPriorMoney()) ? BuyValidCode.buy_success
	// : BuyValidCode.rule_money_error;
	// }

	// /**
	// * 按照购买金额和日期计算
	// *
	// * @param buyMone
	// * @param dt
	// * @return
	// */
	// private BuyValidCode productEndMoneyDt(BigDecimal buyMoney, Date dt) {
	// if (productEndMoney(buyMoney) == BuyValidCode.buy_success) {
	// return productEndDt(dt);
	// }
	// return BuyValidCode.rule_money_error;
	// }

	// /**
	// * 根据规则，返回验证规则的结果
	// *
	// * @param buyMoney
	// * @param dt
	// * @return
	// */
	// private BuyValidCode operRule(BigDecimal buyMoney, Date dt) {
	// if (pcv.getRule() == AppCons.RULE_DATE) {
	// return productEndDt(dt);
	// }
	// if (pcv.getRule() == AppCons.RULE_MONEY) {
	// return productEndMoney(buyMoney);
	// }
	// if (pcv.getRule() == AppCons.RULE_MONEY_DATE) {
	// return productEndMoneyDt(buyMoney, dt);
	// }
	//
	// return BuyValidCode.buy_success;
	// }

	// /**
	// * 处理购买金额的正确性
	// *
	// * @param buyMoney
	// * @return
	// */
	// private BuyValidCode operBuyMoney(BigDecimal buyMoney) {
	// if (!BigDecimalUtils.greatAndEqu(buyMoney,
	// new BigDecimal(pcv.getMinMoney()))) {
	// return BuyValidCode.less_min_money;
	// }
	// if (!BigDecimalUtils.divideByInt(buyMoney,
	// new BigDecimal(pcv.getIncreaMoney()))) {
	// return BuyValidCode.less_increa_money;
	// }
	// return BuyValidCode.buy_success;
	// }

	// /**
	// * 购入的验证
	// *
	// * @return
	// */
	// public ValidResult valid(BigDecimal buyMoney, Date dt) {
	// if (pcv.getType() == AppCons.FIX_FLAG) {
	// if (pcv.getRule() < AppCons.RULE_NONE) {
	// return validFixRule(buyMoney, dt);
	// }
	// return validFix(buyMoney, dt);
	// }
	//
	// if (pcv.getType() == AppCons.FLOAT_FLAG) {
	// return validFloat(buyMoney, dt);
	// }
	// return new ValidResult(BuyValidCode.none_product_type.getCode(),
	// BuyValidCode.none_product_type.getMsg(), false);
	// }

	// /**
	// * 带规则的买入限制,先要验证是否是筹集期，然后在验证金额日期等
	// *
	// * @param buyMoney
	// * @param dt
	// * @return
	// */
	// public ValidResult validFixRule(BigDecimal buyMoney, Date dt) {
	// if (pcv.getStatus() != AppCons.PRODUCT_STATUS_READY) {
	// return new ValidResult(BuyValidCode.rule_status_error.getCode(),
	// BuyValidCode.rule_status_error.getMsg(), false);
	// }
	// return validFix(buyMoney, dt);
	//
	// }

	// /**
	// * 浮动产品类的验证
	// *
	// * @param buyMoney
	// * @param dt
	// * @return
	// */
	// public ValidResult validFloat(BigDecimal buyMoney, Date dt) {
	// BuyValidCode flag;
	// flag = operBuyMoney(buyMoney);
	// if (flag != BuyValidCode.buy_success) {
	// return new ValidResult(flag.getCode(), flag.getMsg(), false);
	// }
	// // 返回正确的结果
	// return new ValidResult(BuyValidCode.buy_success.getCode(),
	// BuyValidCode.buy_success.getMsg(), true);
	// }

	// /**
	// * 验证固定产品类
	// *
	// * @param buyMoney
	// * @param dt
	// * @return
	// */
	// public ValidResult validFix(BigDecimal buyMoney, Date dt) {
	// BuyValidCode flag;
	// // 根据规则的验证
	// flag = operRule(buyMoney, dt);
	// // 返回错误的结果
	// if (flag != BuyValidCode.buy_success) {
	// return new ValidResult(flag.getCode(), flag.getMsg(), false);
	// }
	// // 如果金额不正确
	// flag = operBuyMoney(buyMoney);
	// if (flag != BuyValidCode.buy_success) {
	// return new ValidResult(flag.getCode(), flag.getMsg(), false);
	// }
	// // 返回正确的结果
	// return new ValidResult(BuyValidCode.buy_success.getCode(),
	// BuyValidCode.buy_success.getMsg(), true);
	// }

}
