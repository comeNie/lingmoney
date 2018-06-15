package com.mrbt.lingmoney.bank.utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author Administrator 该类是用来做签名和验签的 （1）签名处理： 1.请将报文使用md5提取报文摘要。
 *         2.再将摘要用已方私钥进行签名。 （2）验签处理： ()
 * 
 */
@SuppressWarnings("restriction")
public class MyRSA {
	public static final String KEY_ALGORITHM = "RSA";

	public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";

	// 商户私钥
	public static String privateString = PropertiesUtil.getPropertiesByKey("LINGMONEY_PRIVATE");
	
	// 商户公钥
	public static String shpublicStr = PropertiesUtil.getPropertiesByKey("LINGMONEY_PUBLIC");
	
	// 银行公钥
	public static String publicStr = PropertiesUtil.getPropertiesByKey("BANK_PUBLIC");

	
	/** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
	public static final int KEY_SIZE = 1024;

	// 签名原文
	public static final String PLAIN_TEXT = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Document><header><channelCode>P2P001</channelCode><channelFlow>20160316175420006</channelFlow><channelDate>20160316</channelDate><channelTime>175420</channelTime><encryptData></encryptData><header><body><XMLPARA>7wPjJiSOm4ucZcU7lq0eqc37HWkJuz1bqjKpo6dgH11wqXi7ffFBzs2xmLOvYIhmAW6AVmky2uBvmIfhc0BTGDCQEbLUsjxZlzTrkHnodoBvOhLVjY/nWb+snb8izM6XuM9rtf2VYuAGkT8idBq+vMTh/sag+ccb7uiGHWmHUQno9bUCtcoA2TaGePIt9MkMIC6+QxRlda6mWzSoUZOj4w==</XMLPARA></body></Document>"; // 原文

	/**
	 * 获取私钥签名
	 * @param data
	 * @return
	 */
	public static String getDataSignature(String data){
		try {
			// 对原报文进行MD5提取摘要：
			String md5Result = MD5(data.replaceAll("\n", ""));
			
			System.out.println("数据生成的MD5:" + md5Result);
			
			// 私钥签名
			PrivateKey privateKey = restorePrivateKey(decryptBASE64(privateString));

			byte[] encodedText = RSAEncode(privateKey, md5Result.getBytes("UTF-8"));

			// 私钥签名后的数据
			// 报文头前面256位的私钥签名后的结果privateResult
			return byteArrayToHexString(encodedText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 公钥解密验签
	 * @param privateResult 
	 * @return
	 */
	public static String getPublicKeyCheck(String privateResult) {
		try {
			//PublicKey publicKey = restorePublicKey(decryptBASE64(shpublicStr));
			PublicKey publicKey = restorePublicKey(decryptBASE64(publicStr));
			String result = RSADecode(publicKey, hexStringToByte(privateResult));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 商户公钥解密验签
	 * @param privateResult 
	 * @return
	 */
	public static String getPublicKeyCheck2(String privateResult) {
		try {
			PublicKey publicKey = restorePublicKey(decryptBASE64(shpublicStr));
			//PublicKey publicKey = restorePublicKey(decryptBASE64(privateString));
			String result = RSADecode(publicKey, hexStringToByte(privateResult));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static void main(String[] args) throws Exception {
		String str = "93A5F7E8BC5D1C72140D498B0AA0C57EB3E6D117163803875E96140700C53E8BA3EFDEEFC38BDB6F2405D0F791476766BE98FFD065B0AF0E7D462D851B08DE4F0D803CDFE21F50940842F504C6623C781F3CE4ADAA51BBB9898C8707F9B4425808568784A2E4795946FB31498AF04FA7D188F9787B3FA7F3B031A434242B895D";
		System.out.println(getPublicKeyCheck2(str));

		// 对原报文进行MD5提取摘要：
		String md5Result = MD5("中文");
		
		System.out.println(md5Result);
		
		
//		System.out.println("MD5摘要: " + md5Result);// 记得转为大写
//
//		// 私钥签名
//		PrivateKey privateKey = restorePrivateKey(decryptBASE64(privateString));
//
//		byte[] encodedText = RSAEncode(privateKey, md5Result.getBytes("UTF-8"));
//
//		// 私钥签名后的数据
//		String privateResult = byteArrayToHexString(encodedText);// 报文头前面256位的私钥签名后的结果privateResult
//		System.out.println("签名后的256位数据 " + privateResult);
//
//		PublicKey publicKey = restorePublicKey(decryptBASE64(publicStr));
//		// 公钥解密
//		System.out.println("公钥解密: " + RSADecode(publicKey, hexStringToByte(privateResult)));
//
//		// 验签的话，解密后的内容与返回的<?xml>后的报文用MD5摘要比对如果一致则未被篡改
//
//		 System.out.println(StringEscapeUtils.unescapeXml("&#25903;&#20184;&#35746;&#21333;&#39044;&#25480;&#26435;&#25104;&#21151;"));
	}

	/**
	 * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
	 * 
	 * @return
	 */
	public static Map<String, byte[]> generateKeyBytes() {

		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGenerator.initialize(KEY_SIZE);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

			Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
			keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
			keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
			return keyMap;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 公钥，X509EncodedKeySpec 用于构建公钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 */
	public static PublicKey restorePublicKey(byte[] keyBytes) {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 */
	public static PrivateKey restorePrivateKey(byte[] keyBytes) {

		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory factory;
		try {
			factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
			return privateKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 签名
	 * 
	 * @param key
	 * @param plainText
	 * @return
	 */
	public static byte[] RSAEncode(PrivateKey key, byte[] plainText) {

		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(plainText);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 验签
	 * 
	 * @param key
	 * @param encodedText
	 * @return
	 */
	public static String RSADecode(PublicKey key, byte[] encodedText) {

		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(encodedText));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	public static String encryptBASE64(byte[] key) throws Exception {

		return (new BASE64Encoder()).encodeBuffer(key);
	}

	// 将字节转换为十六进制字符串
	private static String byteToHexString(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}

	// 将字节数组转换为十六进制字符串
	private static String byteArrayToHexString(byte[] bytearray) {
		String strDigest = "";
		for (int i = 0; i < bytearray.length; i++) {
			strDigest += byteToHexString(bytearray[i]);
		}
		return strDigest;
	}

	// MD5摘要
	public static String MD5(String srcData) throws NoSuchAlgorithmException {
		
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		byte[] btInput = srcData.getBytes();
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		mdInst.update(btInput);
		// 获得密文
		byte[] md = mdInst.digest();
		// 把密文转换成十六进制的字符串形式
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str).toUpperCase();

	}

	// 16进制字符串转为字节数组
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	public static int toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	
	
	
}
