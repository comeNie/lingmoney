package com.mrbt.lingmoney.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mrbt.lingmoney.utils.DateUtils;


public class test {
	
	public static void main(String[] args) {
		
		BigDecimal leftBig = new BigDecimal("0.00");
		BigDecimal rightBig = new BigDecimal("0.00");
		
		System.out.println(leftBig);
		System.out.println(rightBig);
		
		BigDecimal sum = leftBig.add(rightBig);
		
		System.out.println(sum);
		
		System.out.println(leftBig.divide(sum, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
		System.out.println(rightBig.divide(sum, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
	}

	/**
	 * 获取指定区间内的随机数
	 * @param sectionNum
	 * @param reqNum
	 * @return
	 */
	private static final Random RANDOM = new Random();
	public static int[] generatingRandomNumber(int sectionNum, int reqNum) {
		int[] rs = new int[10];// 存放返回结果
		int y = 0;//返回结果下标
		//存放已经生成的数
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		while (map.size() < reqNum) {
			int x = RANDOM.nextInt(sectionNum);
			if (!map.containsKey(x)) {
				map.put(x, "");
				rs[y] = x;
				y++;
			}
		}
		return rs;
	}

	/**
	 * 提供精确加法计算的add方法
	 * 
	 * @param value1
	 *            被加数
	 * @param value2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确减法运算的sub方法
	 * 
	 * @param value1
	 *            被减数
	 * @param value2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确乘法运算的mul方法
	 * 
	 * @param value1
	 *            被乘数
	 * @param value2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供精确的除法运算方法div
	 * 
	 * @param value1
	 *            被除数
	 * @param value2
	 *            除数
	 * @param scale
	 *            精确范围
	 * @return 两个参数的商
	 * @throws IllegalAccessException
	 */
	public static double div(double value1, double value2, int scale)
			throws IllegalAccessException {
		// 如果精确范围小于0，抛出异常信息
		if (scale < 0) {
			throw new IllegalAccessException("精确度不能小于0");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(value1));
		BigDecimal b2 = new BigDecimal(Double.toString(value2));
		System.out.println(b1.divide(b2, scale));
		return b1.divide(b2, scale).doubleValue();
	}
}
