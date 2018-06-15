package com.mrbt.lingmoney.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.utils.AppCons;

/**
 * 验证用户是否登录拦截器 -由手机端传递一个token 用token在redis中取用户信息 验证是否登录
 * 
 * @author YJQ
 *
 */
public class VerifyTokenInterceptor implements HandlerInterceptor {
	@Autowired
	private RedisGet redisGet;

	private static final Logger LOG = LogManager.getLogger(VerifyTokenInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			Object userId = redisGet.getRedisStringResult(AppCons.SESSION_USER + request.getSession().getId());
			if (StringUtils.isEmpty(userId)) {
				LOG.info("来自URL:" + request.getRequestURI() + "的请求，用户未登录。");
				if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
					//ajax请求，返回数据
					response.sendRedirect("/users/unLogin");
				} else {
					response.sendRedirect("/users/login");
				}
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("验证用户登录错误");
			return false;
		}

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
