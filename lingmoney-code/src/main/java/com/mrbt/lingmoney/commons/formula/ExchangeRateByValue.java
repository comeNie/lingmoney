package com.mrbt.lingmoney.commons.formula;

import java.math.BigDecimal;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.mrbt.lingmoney.utils.BigDecimalUtils;

public class ExchangeRateByValue {
	/**
	 * spel的解析类
	 */
	protected ExpressionParser parser = new SpelExpressionParser();
	/**
	 * spel的内容类
	 */
	protected StandardEvaluationContext context;

	/**
	 * 本金
	 */
	public BigDecimal pp;

	/**
	 * 买入的净值
	 */
	public BigDecimal i_nv;
	/**
	 * 卖出的净值
	 */
	public BigDecimal o_nv;

	/**
	 * 份额
	 */
	public BigDecimal num;

	/**
	 * 汇率
	 */
	public BigDecimal r;
	/**
	 * 用户持有的时间
	 */
	public int hold;

	/**
	 * 公司前收取费用的利率
	 */
	public BigDecimal c_pre_r;

	/**
	 * 公司后收取费用的利率
	 */
	public BigDecimal c_after_r;

	public void setPp(BigDecimal pp) {
		this.pp = pp;
	}

	public void setI_nv(BigDecimal i_nv) {
		this.i_nv = i_nv;
	}

	public void setO_nv(BigDecimal o_nv) {
		this.o_nv = o_nv;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public void setR(BigDecimal r) {

		this.r = r;
	}

	public void setHold(int hold) {
		this.hold = hold;
	}

	public void setC_pre_r(BigDecimal c_pre_r) {
		this.c_pre_r = c_pre_r;
	}

	public void setC_after_r(BigDecimal c_after_r) {
		this.c_after_r = c_after_r;
	}

	/**
	 * 获取值
	 * 
	 * @param formula
	 * @return
	 */
	public BigDecimal getValue(String formula) {
		this.context = new StandardEvaluationContext(this);
		return BigDecimalUtils.money(parser.parseExpression(formula).getValue(
				context, BigDecimal.class));
	}
}
