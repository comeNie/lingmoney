package com.mrbt.lingmoney.mobile.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.mrbt.lingmoney.commons.cache.RedisDao;
/**
 * 
 * @author ruochen.yu
 *
 */
public class BaseController {

	@Autowired
	private RedisDao redisDao;
	/**
	 * redis根据key获取value
	 * @param key key
	 * @return uid
	 */
	public String getUid(String key) {
		String uid = (String) redisDao.get(key);
		return uid;
	}

	/**
	 * 接收银行xml报文
	 * @param request req
	 * @return string
	 * @throws Exception e
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
