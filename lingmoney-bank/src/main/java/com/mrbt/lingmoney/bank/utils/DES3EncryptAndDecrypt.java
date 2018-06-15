package com.mrbt.lingmoney.bank.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DES3EncryptAndDecrypt {

	private static String ALGORITHM = "DESede";

	// 3des应用加密密码（由华兴银行统一分配)
	private static String Des3Key = PropertiesUtil.getPropertiesByKey("DES3KEY");
	

	public static String DES3EncryptMode(String src) throws Exception {
		
		String result = src.substring(src.indexOf(">") + 1, src.lastIndexOf("<"));

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		DESedeKeySpec desKeySpec = new DESedeKeySpec(Des3Key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return new String(Base64.encodeBase64(cipher.doFinal(result.getBytes("UTF-8"))));
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
//		String TDATA = "" + "<TDATA>" + "<MERCHANTID>P2P001</MERCHANTID>" + "<MERCHANTNAME>P2P测试</MERCHANTNAME>"
//				+ "<ACNO>6236882299000000435</ACNO>" + "<ACNAME>PTP测试7</ACNAME>" + "</TDATA>";
	
		String TDATA = "<MERCHANTID>XJP</MERCHANTID><APPID>PC</APPID><TTRANS>6</TTRANS><MERCHANTNAME>小金爸</MERCHANTNAME><ACNAME>1010</ACNAME><IDTYPE>1010</IDTYPE><IDNO>440106198901020044</IDNO><MOBILE>13460000587</MOBILE><EMAIL></EMAIL><RETURNURL>http://www.kk.com/kksiek.html</RETURNURL><CUSTMNGRNO></CUSTMNGRNO>";
		
		String XMLPARA = DES3EncryptMode(TDATA);
		return XMLPARA;
	}

	public static void main(String args[]) throws Exception {
		String encrypt = ZTSA54Q67();

		System.out.println("加密后的内容：     " + encrypt);
		System.out.println("解密后的内容：     " + DES3DecryptMode("/2edtkwof4YTxFer3SefyMWFQnavXlnY/QLSSNHRGS1uIKtH0pcN3EIXkxlDVi/HK13/59XJMmR+3chQov4qNv9nnbZMKH+GHTwgeo/9x2oUa/4dQXwr01dq6RmWVf4XmNhPyfW3ySh39Q4yCpCeBkTKD7X2pYkRVSeQTjkmXRWT6r8sDnt7Vh8aHvVLU8/Ci7HxxsZSIfPrWrR4cLUHZRD0JJJQkFGTtleu7VX+PMFkp2aIVbOgN50yc5HMZgRiM2yUgoiDtBvmqIqhe9Geun36HSb4TljJbIDvi13KIy27860vz7EXaYFzygzmaySSg5z1eYcBZOY0mEkY4DRqxiP451DP9JL3nH/5mWRR2xLYD3S/XQNk54rrA+5TzYfvcXdobIoEYrtEI6bWkwg3ew=="));
	}
}
