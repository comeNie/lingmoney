package com.mrbt.lingmoney.admin.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mrbt.lingmoney.utils.AppCons;

/**
 * 验证用户是否登录的拦截器
 * 
 * @author administrator
 * @date 2017年5月4日 上午9:34:37
 * @description session中取用户信息
 * @version 1.0
 * @since 2017-04-12
 */
public class SecurityInterceptor implements HandlerInterceptor {
	private List<String> excludedUrls;

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	/**
	 * 拦截器
	 * @param	request  request
	 * @param	response  response
	 * @param	handler  handler
	 * @return ret
	 * @throws Exception	Exception
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUri = request.getRequestURI();

		for (String url : excludedUrls) {
			if (requestUri.indexOf(url) >= 0) {
				return true;
			}
		}
		HttpSession session = request.getSession();
		if (session.getAttribute(AppCons.SESSION_USER) == null) {
			response.sendRedirect("/index.html");
		} else {
			return true;
		}
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
