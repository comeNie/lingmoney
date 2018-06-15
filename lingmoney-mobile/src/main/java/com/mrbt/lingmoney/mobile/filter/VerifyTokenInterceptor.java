package com.mrbt.lingmoney.mobile.filter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultBean;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultUtils;

/**
 * 验证用户是否登录拦截器 -由手机端传递一个token 用token在redis中取用户信息 验证是否登录
 * 
 * @author YJQ
 *
 */
public class VerifyTokenInterceptor implements HandlerInterceptor {
	@Autowired
	private RedisGet redisGet;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		PageInfo pageInfo = new PageInfo();
		PrintWriter out = response.getWriter();
		try {
			
			String token = request.getParameter("token");
			
			if (StringUtils.isEmpty(token) || StringUtils.isEmpty(redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token))) {
				pageInfo.setCode(ResultInfo.LOGIN_FAILURE.getCode());
				pageInfo.setMsg("用户登录已过期");
				// 需加密处理
				out.print(JSONObject.toJSON(new ResultBean(ResultUtils.getEncodeResult(pageInfo))));
				return false;
			}
			response.reset();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("验证用户登录错误");
			out.print(JSONObject.toJSON(new ResultBean(ResultUtils.getEncodeResult(pageInfo))));
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
