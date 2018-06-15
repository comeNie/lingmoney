package com.mrbt.lingmoney.commons.formula;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 获取汇率根据formula的值
 * 
 * @author lzp
 *
 */
public class ExchangeRateByFormula extends ExchangeRateByValue {
	/**
	 * 前置佣金的formula
	 */
	public String c_pre_r_formula;
	/**
	 * 后置佣金收取的formula
	 */
	public String c_after_r_formula;
	/**
	 * 固定汇率，按天的formula
	 */
	public String fix_day_r_formula;
	/**
	 * 固定汇率，按月的formula
	 */
	public String fix_month_r_formula;
	/**
	 * 浮动产品的计算公式
	 */
	public String float_formula;
	/**
	 * 浮动产品_后收费的计算公式
	 */
	public String float_after_formula;

	/**
	 * 获取前置佣金
	 * 
	 * @return
	 */
	public BigDecimal c_pre_r_value() {
		if (StringUtils.isNotBlank(c_pre_r_formula)) {
			return this.getValue(c_pre_r_formula);
		}
		return null;
	}

	/**
	 * 获取后置佣金
	 * 
	 * @return
	 */
	public BigDecimal getC_after_r_value() {
		if (StringUtils.isNotBlank(c_after_r_formula)) {
			return this.getValue(c_after_r_formula);
		}
		return null;
	}

	/**
	 * 获取固定汇率——按天费用
	 * 
	 * @return
	 */
	public BigDecimal getFix_day_r_value() {
		if (StringUtils.isNotBlank(fix_day_r_formula)) {
		    
		    BigDecimal value = this.getValue(fix_day_r_formula);
		    
		    if (value.doubleValue()>0)
		    {
			return value;
		    }
		    else
		    {
			return new BigDecimal (0);
		    }
		}
		return null;
	}

	/**
	 * 获取固定汇率——按月费用
	 * 
	 * @return
	 */
	public BigDecimal getFix_month_r_value() {
		if (StringUtils.isNotBlank(fix_month_r_formula)) {
			return this.getValue(fix_month_r_formula);
		}
		return null;
	}

	/**
	 * 获取浮动产品值
	 * 
	 * @return
	 */
	public BigDecimal getFloat_value() {
		if (StringUtils.isNotBlank(float_formula)) {
			return this.getValue(float_formula);
		}
		return null;
	}

	/**
	 * 获取浮动产-后佣金值
	 * 
	 * @return
	 */
	public BigDecimal getFloat_after_value() {
		if (StringUtils.isNotBlank(float_after_formula)) {
			return this.getValue(float_after_formula);
		}
		return null;
	}

	public void setC_pre_r_formula(String c_pre_r_formula) {
		this.c_pre_r_formula = c_pre_r_formula;
	}

	public void setC_after_r_formula(String c_after_r_formula) {
		this.c_after_r_formula = c_after_r_formula;
	}

	public void setFix_day_r_formula(String fix_day_r_formula) {
		this.fix_day_r_formula = fix_day_r_formula;
	}

	public void setFix_month_r_formula(String fix_month_r_formula) {
		this.fix_month_r_formula = fix_month_r_formula;
	}

	public void setFloat_formula(String float_formula) {
		this.float_formula = float_formula;
	}

	public void setFloat_after_formula(String float_after_formula) {
		this.float_after_formula = float_after_formula;
	}

}
