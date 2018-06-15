package com.mrbt.lingmoney.mobile.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mrbt.lingmoney.commons.cache.RedisGet;

/**
 * 银行接口开关拦截器
 * @author luox
 * @Date 2017年6月16日
 */
public class BankInterfaceSwitchInterceptor implements HandlerInterceptor {
	
	@Autowired
	private RedisGet redisGet;
	 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		// 不是银行接口相关请求，放过
		if (!requestURI.contains("/bank/")) {
			return true;
		}
		// 是银行接口相关请求
		Object closeBankinterfac = redisGet.getRedisStringResult("CLOSE_BANKINTERFACE");
		if (closeBankinterfac == null) {
			// 不关闭,放过
			return true;
		}
		
		// 要关闭，查询关闭的起始时间
		Object bankInterfaceClosetime = redisGet.getRedisStringResult("BANKINTERFACE_CLOSETIME");
		
		if (bankInterfaceClosetime != null) {
			
			long closeTime = Long.valueOf((String) bankInterfaceClosetime);
			// 当前时间
			long currentTimeMillis = System.currentTimeMillis();
			
			if (currentTimeMillis < closeTime) {
				// 未到关闭时间，放过
				return true;
			}
		}
		
		// 关闭的时间如果没有配置，或者到了关闭的时间，提示银行接口关闭
		Object bankInterfaceCloseMessage = redisGet.getRedisStringResult("BANKINTERFACE_CLOSE_MESSAGE");
		response.getWriter().write((String) bankInterfaceCloseMessage);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
