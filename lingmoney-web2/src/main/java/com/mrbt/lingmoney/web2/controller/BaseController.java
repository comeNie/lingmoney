package com.mrbt.lingmoney.web2.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.utils.PropertiesUtil;

/**
 * 
 * 基类
 *
 */
public class BaseController {
	
	public static final String HX_CALL_BACK_OPER_PAGE = PropertiesUtil.getPropertiesByKey("HX_CALL_BACK_OPER_PAGE");
	
	/**
	 * 华兴银行APPID
	 */
	public static final String APP_ID = "PC";
	
	/**
	 * 华兴银行客户端类型，用来区分交易吗
	 */
	public static final int CLIENT_TYPE = 0;

	@Autowired
	private RedisDao redisDao;

	/**
	 * 获取用户id
	 * 
	 * @param key
	 *            key
	 * @return 返回信息
	 */
	public String getUid(String key) {
		String uid = (String) redisDao.get(key);
		return uid;
	}

	/**
	 * 获取XmlDocument
	 * 
	 * @param request
	 *            请求
	 * @return 返回信息
	 * @throws Exception
	 *             异常
	 */
	public String getXmlDocument(HttpServletRequest request) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String buffer = null;

		StringBuffer xml = new StringBuffer();

		while ((buffer = br.readLine()) != null) {
			xml.append(buffer);
		}
		return xml.toString();
	}

}
