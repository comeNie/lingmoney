package com.mrbt.lingmoney.utils;

import java.io.StringWriter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author luox
 * @Date 2017年4月24日
 */
public class ResultUtils {

	private ResultUtils(){}
	
	public static String getEncodeResult(String source){
		if(source == null) return null;
		return AES256Encryption.aes256Encrypt(source);
	}
	
	public static String getEncodeResult(Object obj){
		if(obj == null) return null;
		String result = null;
		try {
			ObjectMapper om = new ObjectMapper();
			om.setSerializationInclusion(Include.NON_NULL);
			StringWriter sw = new StringWriter();
			om.writeValue(sw, obj);
			result = AES256Encryption.aes256Encrypt(sw.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

