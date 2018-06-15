package com.mrbt.lingmoney.utils.ip;
import java.io.UnsupportedEncodingException;  
import java.util.StringTokenizer;  
  
import org.apache.log4j.Level;

import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;  
  
  
  
  
/** 
 * 工具类，提供一些方便的方法 
 */  
public final class Util {  
	
	private Util() {
		
	}
      
    private static StringBuilder sb = new StringBuilder();  
    /** 
     * 从ip的字符串形式得到字节数组形式 
     * @param ip 字符串形式的ip 
     * @return 字节数组形式的ip 
     */  
    public static byte[] getIpByteArrayFromString(String ip) {  
        byte[] ret = new byte[ResultNumber.FOUR.getNumber()];  
        StringTokenizer st = new StringTokenizer(ip, ".");  
        try {  
            ret[ResultNumber.ZERO.getNumber()] = (byte) (Integer.parseInt(st.nextToken()) & ResultNumber.LXFF.getNumber());  
            ret[ResultNumber.ONE.getNumber()] = (byte) (Integer.parseInt(st.nextToken()) & ResultNumber.LXFF.getNumber());  
            ret[ResultNumber.TWO.getNumber()] = (byte) (Integer.parseInt(st.nextToken()) & ResultNumber.LXFF.getNumber());  
            ret[ResultNumber.THREE.getNumber()] = (byte) (Integer.parseInt(st.nextToken()) & ResultNumber.LXFF.getNumber());  
        } catch (Exception e) {  
          LogFactory.log("从ip的字符串形式得到字节数组形式报错", Level.ERROR, e);  
        }  
        return ret;  
    }  
    /** 
     * @param ip ip的字节数组形式 
     * @return 字符串形式的ip 
     */  
    public static String getIpStringFromBytes(byte[] ip) {  
        sb.delete(0, sb.length());  
        sb.append(ip[ResultNumber.ZERO.getNumber()] & ResultNumber.LXFF.getNumber());  
        sb.append('.');       
        sb.append(ip[ResultNumber.ONE.getNumber()] & ResultNumber.LXFF.getNumber());  
        sb.append('.');       
        sb.append(ip[ResultNumber.TWO.getNumber()] & ResultNumber.LXFF.getNumber());  
        sb.append('.');       
        sb.append(ip[ResultNumber.THREE.getNumber()] & ResultNumber.LXFF.getNumber());  
        return sb.toString();  
    }  
      
    /** 
     * 根据某种编码方式将字节数组转换成字符串 
     * @param b 字节数组 
     * @param offset 要转换的起始位置 
     * @param len 要转换的长度 
     * @param encoding 编码方式 
     * @return 如果encoding不支持，返回一个缺省编码的字符串 
     */  
    public static String getString(byte[] b, int offset, int len, String encoding) {  
        try {  
            return new String(b, offset, len, encoding);  
        } catch (UnsupportedEncodingException e) {  
            return new String(b, offset, len);  
        }  
    }  
}  
