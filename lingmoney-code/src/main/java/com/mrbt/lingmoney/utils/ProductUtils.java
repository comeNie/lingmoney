package com.mrbt.lingmoney.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.mrbt.lingmoney.model.Product;

/**
 * 推广期活动产品
 * 
 * @author airgilbert
 *
 */
public class ProductUtils {
	public static ResourceBundle prb;
	static {
		prb = ResourceBundle.getBundle("product_active");
	}

	public static String getContent(String key) {
		if (prb == null) {
			return "";
		}
		String value = prb.getString(key);
		return value;
	}

	public static int getIntContent(String key) {
		String value = getContent(key);
		if (StringUtils.isNotBlank(value)) {
			return Integer.parseInt(value);
		}
		return -1;
	}

	public static BigDecimal getBigDecimalContent(String key) {
		String value = getContent(key);
		if (StringUtils.isNotBlank(value)) {
			return new BigDecimal(value);
		}
		return null;
	}

	/**
	 * 获取理财天数
	 * 
	 * @param product
	 * @return
	 */
	public static Integer getFinancialDays(Product product) {
		Integer days = null;
		if (product.getUnitTime() == 0) {
			days = product.getfTime();
		} else if (product.getUnitTime() == 1) {
			days = product.getfTime() * 7;
		} else if (product.getUnitTime() == 2) {
			days = product.getfTime() * 30;
		} else if (product.getUnitTime() == 3) {
			days = product.getfTime() * 365;
		} else {
			days = 0;
		}
		return days;
	}

	/**
	 * 计算利息 = 理财金额*产品年利率*理财天数/365 、四舍五入
	 * 
	 * @param money
	 *            购买金额
	 * @param days
	 *            天数
	 * @param yield
	 *            利率
	 * @return
	 */
	public static BigDecimal countInterest(BigDecimal money, Integer days, BigDecimal yield) {
		System.out.println("计算利息：金额：" + money + ",天数：" + days + ",年利率：" + yield);

		BigDecimal interest = new BigDecimal("0");
		try {
			interest = money.multiply(yield).multiply(new BigDecimal(days))
					.divide(new BigDecimal("365"), 2, RoundingMode.HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("计算所得利息为：" + interest);

		return interest;
	}
}