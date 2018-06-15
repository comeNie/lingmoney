package com.mrbt.lingmoney.utils;

import java.util.HashMap;
import java.util.List;

/**
 * 返回值的帮助类
 * 
 * @author lzp
 *
 */
public class ResponseUtils {
	/**
	 * 成功的返回码
	 */
	public static int SUCCESS_CODE = 200;
	/**
	 * 参数错误
	 */
	public static int ERROR_PARAM = 400;
	/**
	 * 服务器错误
	 */
	public static int ERROR_SERVER = 500;
	/**
	 * 结果已经存在
	 */
	public static int ERROR_RESULT_EXIST = 600;
	/**
	 * session超时
	 */
	public static int ERROR_SESSION_INVALID = 700;

	/**
	 * 返回的字符串格式
	 */
	private static String RESPONSE_STR = "code:{0},msg:\"{1}\"";

	/**
	 * 返回带message的json串
	 * 
	 * @param message
	 * @return
	 */
	public static HashMap<String, Object> success(String message) {
		return toResponseObject(SUCCESS_CODE, message);
	}

	public static HashMap<String, Object> success() {
		return success("success");
	}

	/**
	 * 返回失败的json串
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	public static HashMap<String, Object> failure(int code, String message) {
		return toResponseObject(code, message);
	}

	/**
	 * 返回json的结果字符串
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @return
	 */
	private static HashMap<String, Object> toResponseObject(int code,
			String message) {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("code", code);
		reMap.put("msg", message);
		return reMap;
	}
	
	

	/**
	 * 返回带list的json串
	 * 
	 * @param list
	 * @return
	 */
	public static HashMap<String, Object> success(List list) {
		return toResponseObject(SUCCESS_CODE, list);
	}

	/**
	 * 返回json的结果list
	 * 
	 * @param list
	 *            需要转换的list
	 * @return
	 */
	private static HashMap<String, Object> toResponseObject(int code,
			List list) {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("code", code);
		reMap.put("msg", list);
		return reMap;
	}
	
	
	/**
	 * 返回带Object的json串
	 * 
	 * @param object
	 * @return
	 */
	public static HashMap<String, Object> success(Object object) {
		return toResponseObject(SUCCESS_CODE, object);
	}

	/**
	 * 返回json的结果list
	 * 
	 * @param list
	 *            需要转换的list
	 * @return
	 */
	private static HashMap<String, Object> toResponseObject(int code,
			Object object) {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("code", code);
		reMap.put("msg", object);
		return reMap;
	}

}
