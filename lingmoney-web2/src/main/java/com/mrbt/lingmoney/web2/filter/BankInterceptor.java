package com.mrbt.lingmoney.web2.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月26日
 */
public class BankInterceptor implements HandlerInterceptor {
	private static final String CHAR_ENCODING = "UTF-8";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		// 不是银行接口相关请求，放过
		if (!requestURI.contains("/bank/")) {
			return true;
		}
		// 是银行接口，给出提示
		response.sendRedirect("/users/systemUpdate");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
