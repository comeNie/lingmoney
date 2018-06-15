package com.mrbt.lingmoney.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 生成短UUID，用于用户推荐码
 * 
 * @author Administrator
 *
 */
public class ShortUUID {
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString().toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(verification(10000000));
	}

	public static int verification(int y) {
		Map<String, String> map = new HashMap<String, String>();
		int x = 0;
		for (int i = 0; i < y; i++) {
			String key = generateShortUuid().toUpperCase();

			if (map.containsKey(key)) {
				x++;
			} else {
				map.put(key, "");
			}
		}
		return x;
	}
}
