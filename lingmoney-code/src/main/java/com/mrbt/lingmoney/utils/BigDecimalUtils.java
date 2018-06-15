package com.mrbt.lingmoney.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数值计算的辅助类
 * 
 * @author lzp
 *
 */
public class BigDecimalUtils {
	/**
	 * 保留两位小数
	 * 
	 * @param tmp
	 * @return
	 */
	public static BigDecimal money(BigDecimal tmp) {
		return tmp.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * src<=dest返回真，否则返回假
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean lessAndEqu(BigDecimal src, BigDecimal dest) {
		return src.compareTo(dest) > 0 ? false : true;
	}

	/**
	 * src>=dest返回真，否则返回假
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean greatAndEqu(BigDecimal src, BigDecimal dest) {
		return src.compareTo(dest) >= 0 ? true : false;
	}

	/**
	 * 购买金额是否为递增金额的整数倍
	 * 
	 * @param buyMoney
	 * @param increaMoney
	 * @return
	 */
	public static boolean divideByInt(BigDecimal buyMoney,
			BigDecimal increaMoney) {
		BigDecimal result = buyMoney.divide(increaMoney, 6, RoundingMode.DOWN);
		return result.compareTo(new BigDecimal(result.intValue())) == 0 ? true
				: false;
	}

}
