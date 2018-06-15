package com.wdzg.datacount;

import java.io.UnsupportedEncodingException;

import com.mrbt.lingmoney.utils.AES256Encryption;

import net.sf.json.JSONObject;

public class Aes256Test {
	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static void main(String[] args){
		
		JSONObject json = new JSONObject();
		json.put("isRecommend", 1);
		
		
		String str = json.toString();
		System.out.println("原文：" + str);

		
		
		// 初始化密钥
		try {
			//加密数据
			String encData = AES256Encryption.aes256Encrypt(str);
			System.out.println("加密后的数据:" + encData);
			
			// 解密数据
			String decData = AES256Encryption.aes256Decrypt(encData);
			System.out.println("解密后的数据:" + decData);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
