package com.mrbt.lingmoney.bank;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import sun.misc.BASE64Encoder;

/**
 * 不需修改任何参数，直接运行即可
 * @author zhoujiajun
 *
 */
public class CreateKey {
	
	private static final String PUBLIC_KEY="RSAPublicKey";
	
	private static final String PRIVATE_KEY="RSAPrivateKey";
	
	public static void main(String[] args) throws Exception {
		Map<String,Object> keyMap = initKey();
		System.out.println("生成的公钥为："+getPublicKey(keyMap));
		System.out.println("生成的私钥为："+getPivateKey(keyMap));
	}
	
	public static Map<String,Object> initKey() throws Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);//生成秘钥的长度，勿动
		KeyPair kp = kpg.generateKeyPair();//生成密钥对
		RSAPublicKey puk = (RSAPublicKey)kp.getPublic();
		RSAPrivateCrtKey prk = (RSAPrivateCrtKey) kp.getPrivate();
		Map<String,Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, puk);
		keyMap.put(PRIVATE_KEY, prk);
		return keyMap;
	}
	
	public static String getPublicKey(Map<String,Object> keyMap) throws Exception{
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		byte[] publicKey = key.getEncoded();
		return encryptBASE64(publicKey); 
	}
	
	public static String getPivateKey(Map<String,Object> keyMap) throws Exception{
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		byte[] pivateKey = key.getEncoded();
		return encryptBASE64(pivateKey); 
	}
	//Base64编码
	public static String encryptBASE64(byte[] key) throws Exception{
		return (new BASE64Encoder()).encodeBuffer(key);
	}
}
