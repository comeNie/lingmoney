package com.mrbt.lingmoney.utils;

import java.util.Random;

/**
 * 生成验证码
 * @author Administrator
 *
 */
public class CodeUtils {
	private static String[] s = { "C", "E", "M", "D", "U", "P", "A", "E", "X", "J" };

	public static String getCode(int id) {

		StringBuffer buffer = new StringBuffer();
		String id_str = String.valueOf(id);

		int len = id_str.length();
		for (int i = 0; i < len; i++) {
			buffer.append(s[Integer.parseInt(id_str.substring(i, i + 1))]);
		}

		// 随机数
		for (int i = 0; i < (8 - len); i++) {
			int value = (int) (Math.random() * 10);
			buffer.append(String.valueOf(value));
		}

		return buffer.toString();
	}

	public static String getRandomCode() {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 1;
		for (int i = 0; i < 5; i++)
			result = result * 10 + array[i];

		return result + "";
	}

	public static void main(String[] args) {
		// getCode(23);
		System.out.println(getRandomCode());
	}
}
