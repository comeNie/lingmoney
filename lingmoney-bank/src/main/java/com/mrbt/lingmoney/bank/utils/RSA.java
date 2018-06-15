package com.mrbt.lingmoney.bank.utils;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author Administrator 仅供参考用 生成公钥和私钥 交换公私钥，请见公钥华兴银行，以便验签
 */
@SuppressWarnings("restriction")
public class RSA {

	private static String PUBLIC_KEY = "RSAPublicKey";

	private static String PRIVATE_KEY = "RSAPrivateKey";

	private static String ALGORITHM = "DESede";

	// 3des应用加密密码（由华兴银行统一分配)
	public static String Des3Key = PropertiesUtil.getPropertiesByKey("DES3KEY");

	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024); // 指定密钥的长度，初始化密钥对生成器
		KeyPair kp = kpg.generateKeyPair(); // 生成密钥对
		RSAPublicKey puk = (RSAPublicKey) kp.getPublic();
		RSAPrivateCrtKey prk = (RSAPrivateCrtKey) kp.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, puk);
		keyMap.put(PRIVATE_KEY, prk);
		return keyMap;
	}

	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		byte[] publicKey = key.getEncoded();
		return encryptBASE64(publicKey);
	}

	public static String getPivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		byte[] pivateKey = key.getEncoded();
		return encryptBASE64(pivateKey);
	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static String DES3EncryptMode(String src) throws Exception {

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		DESedeKeySpec desKeySpec = new DESedeKeySpec(Des3Key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return new String(Base64.encodeBase64(cipher.doFinal(src.getBytes("UTF-8"))));
	}

	public static String DES3DecryptMode(String src) throws Exception {

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		DESedeKeySpec desKeySpec = new DESedeKeySpec(Des3Key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] dd = cipher.doFinal(Base64.decodeBase64(src.getBytes("UTF-8")));
		return new String(dd, "UTF-8");
	}

	/** 余额查询 **/
	public static String ZTSA54Q67() throws Exception {
		String TDATA = "" + "<TDATA>" + "<MERCHANTID>P2P001</MERCHANTID>" + "<MERCHANTNAME>P2P测试</MERCHANTNAME>"
				+ "<ACNO>6236882299000000435</ACNO>" + "<ACNAME>PTP测试7</ACNAME>" + "</TDATA>";
		String XMLPARA = DES3EncryptMode(TDATA);
		return XMLPARA;
	}

	public static void main(String args[]) throws Exception {
		// int keylen = getRandom();
		Map<String, Object> testMap = initKey();
		System.out.println("生成公钥：     " + getPublicKey(testMap));
		System.out.println("生成私钥：     " + getPivateKey(testMap));

		String encrypt = ZTSA54Q67();

		System.out.println("加密后的内容：     " + encrypt);
		System.out.println("解密后的内容：     " + DES3DecryptMode(encrypt));
	}

}
