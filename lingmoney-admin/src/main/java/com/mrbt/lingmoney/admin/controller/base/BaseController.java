package com.mrbt.lingmoney.admin.controller.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 后台管理基础controller
 * @author Administrator
 *
 */
public class BaseController {
	/**
	 * 初始化时间，带时分秒
	 * 
	 * @param binder web时间对象
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * 接收银行xml报文
	 * @param request req
	 * @return	返回XML报文
	 * @throws Exception 异常处理
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
