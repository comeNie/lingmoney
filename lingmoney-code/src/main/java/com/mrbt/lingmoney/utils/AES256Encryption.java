package com.mrbt.lingmoney.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONObject;

/**
 * Aes256位加密解密
 * @author Administrator
 *
 */
public class AES256Encryption {

	/**
	 * 密钥算法 java6支持56位密钥，bouncycastle支持64位
	 */
	private static final String KEY_ALGORITHM = "AES";

	/**
	 * 固定KEY
	 */
	static byte[] key = new byte[] { 0x0b, 0x09, 0x04, 0x0b, 0x02, 0x0f, 0x0b, 0x0c, 0x01, 0x03, 0x09, 0x07, 0x0c, 0x03,
			0x0f, 0x0a, 0x04, 0x0f, 0x1a, 0x0f, 0x0e, 0x09, 0x05, 0x01, 0x0a, 0x0a, 0x01, 0x0e, 0x06, 0x07, 0x09,
			0x0d };

	/**
	 * 加密/解密算法/工作模式/填充方式
	 * 
	 * JAVA6 支持PKCS5PADDING填充方式 Bouncy castle支持PKCS7Padding填充方式
	 */
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 */
	private static Key toKey(byte[] key) throws Exception {
		// 实例化DES密钥
		// 生成密钥
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
		return secretKey;
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密后的数据
	 */
	private static byte[] encrypt(byte[] data) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		/**
		 * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密后的数据
	 */
	private static byte[] decrypt(byte[] data) throws Exception {
		// 欢迎密钥
		Key k = toKey(key);
		/**
		 * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 包装加密
	 * @param sourceData
	 * @return	base64加密串
	 */
	public static String aes256Encrypt(String sourceData) {
		String dataBase64 = "";
		try {
			
			byte[] data = AES256Encryption.encrypt(sourceData.getBytes());
			
//			System.out.print("加密后：");
//			for (int i = 0; i < data.length; i++) {
//				System.out.printf("%x", data[i]);
//			}
//			System.out.print("\n");

			dataBase64 = Base64.encodeBase64String(data);

//			System.out.println("把byte类型的data, base64后：" + dataBase64);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataBase64;
	}
	
	/**
	 * 包装解密
	 * @param encData
	 * @return
	 */
	public static String aes256Decrypt(String encData){
		byte[] data = null;
		try {
			data = AES256Encryption.decrypt(Base64.decodeBase64(encData));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(data);
	}
	

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {

		JSONObject json = new JSONObject();
		json.put("u_id", "aaaaaaaaaa");
		
		String str = json.toString();
		System.out.println("原文：" + str);

		// 初始化密钥
		try {
			System.out.print("密钥：");
			for (int i = 0; i < key.length; i++) {
				System.out.printf("%x", key[i]);
			}
			System.out.print("\n");

			//加密数据
			String encData = aes256Encrypt(str);
			System.out.println("加密后的数据:" + encData);
			
			// 解密数据
			String decData = aes256Decrypt("jmp/dHN+Ic7kwfqj+wX8nO65hTH6EMFrvGwyK7++SkxhGeOxyaMxR31nwG5vnpPEvS08qShzsv7mozcTIM1ItMjxhTPv9Sn+ZUUfoqu/NrE=");
			System.out.println("解密后的数据:" + decData);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}