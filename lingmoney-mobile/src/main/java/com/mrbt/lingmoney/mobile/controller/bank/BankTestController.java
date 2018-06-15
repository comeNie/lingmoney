package com.mrbt.lingmoney.mobile.controller.bank;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试银行接口通用页面
 * @author ruochen.yu
 *
 */
@Controller
@RequestMapping("/testBank")
public class BankTestController {
	
	private static final Logger LOGGER = LogManager.getLogger(BankTestController.class);
	/**
	 * @param request res
	 * @param response req
	 * @return string
	 */
	@RequestMapping("/index")
	public String testBankPage(HttpServletRequest request, HttpServletResponse response) {
		return "hxBank/index";
	}
	/**
	 * @param request res
	 * @param response req
	 * @return string
	 */
	@RequestMapping("/bankCall")
	public String bankCall(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("手机端统一返回商户URL");
		try {
			response.getWriter().write("bankCall");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
