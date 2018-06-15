package com.mrbt.lingmoney.utils;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class IOUtils {
	/**
	 * Base64字符串转换成InputStream
	 * @author YJQ  2017年5月24日
	 * @param base64Str
	 * @return
	 */
	public static InputStream Base64ToInputStream(String base64Str) throws Exception{ // 对字节数组字符串进行Base64解码并生成图片  
    	if (base64Str == null) 
            return null;  
        BASE64Decoder decoder = new BASE64Decoder();  
        // Base64解码  
        byte[] b = decoder.decodeBuffer(base64Str.replace("\\", ""));  
        return new ByteArrayInputStream(b);  
    }  
	
	/**
	 * Base64字符串转换成OutputStream
	 * @author YJQ  2017年5月24日
	 * @param base64Str
	 * @param filePath
	 * @throws Exception
	 */
	public static void Base64ToOutputStream(String base64Str,String filePath) throws Exception{
		BASE64Decoder decoder = new BASE64Decoder(); 
		 byte[] b = decoder.decodeBuffer(base64Str.replace("\\", ""));  
         for (int i = 0; i < b.length; ++i) {  
             if (b[i] < 0) {// 调整异常数据  
                 b[i] += 256;  
             }  
         }  
         OutputStream out = new FileOutputStream(filePath);  
         out.write(b);  
         out.flush();  
         out.close();  
	}
}
