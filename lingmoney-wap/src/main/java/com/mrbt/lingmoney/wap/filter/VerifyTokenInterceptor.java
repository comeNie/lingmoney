package com.mrbt.lingmoney.wap.filter;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 验证用户是否登录拦截器 -sesson redis中取用户信息 验证是否登录
 * 
 * @author YJQ
 *
 */
@Component
public class VerifyTokenInterceptor implements HandlerInterceptor {
	@Autowired
	private RedisGet redisGet;

	private static final Logger LOG = LogManager.getLogger(VerifyTokenInterceptor.class);

	private static final String CHAR_ENCODING = "UTF-8";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean ret = true;
		try {
			Object userId = redisGet.getRedisStringResult(AppCons.SESSION_USER + request.getSession().getId());
			LOG.info("来自客户端IP" + getRemoteHost(request) + "\t访问URL:" + request.getRequestURI() + "\tkey:" + userId);
			if (StringUtils.isEmpty(userId)) {
				LOG.info("来自客户端IP" + getRemoteHost(request) + "\t访问URL:" + request.getRequestURI() + "的请求，用户未登录。");
				Map<String, Object> resMap = new HashMap<String, Object>();
				
				response.setHeader("Content-type", "text/html;charset=UTF-8");  
				//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859  
				response.setCharacterEncoding(CHAR_ENCODING);
				PrintWriter out = response.getWriter();
				
				resMap.put("code", ResultInfo.LOGIN_FAILURE.getCode());
				resMap.put("msg", ResultInfo.LOGIN_FAILURE.getMsg());
				
				out.write(resMap.toString());
				ret = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("验证用户登录错误");
			ret = false;
		}
		return ret;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
	
	/**
	 * 获取访问客户端的IP地址
	 * @param request	HttpServletRequest
	 * @return 返回IP地址
	 */
	public String getRemoteHost(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
