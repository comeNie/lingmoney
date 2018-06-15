package com.mrbt.lingmoney.notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/testNotice")
public class TestNoticeController {
	
	/**
	 * 测试银行回调测试页面
	 * @param response
	 * @param request
	 * @param version
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "testPage")
	public String update(HttpServletResponse response, HttpServletRequest request) {
		return "hxBank/index";
	}
}
