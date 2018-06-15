package com.mrbt.lingmoney.wap.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 全局跨域过滤器
 * 
 * @author Administrator
 *
 */
@Component
public class CorsFilter implements Filter {
	
	private static final Logger LOGGER = LogManager.getLogger(VerifyTokenInterceptor.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		String origin = (String) servletRequest.getRemoteHost() + ":" + servletRequest.getRemotePort();
		LOGGER.info("访问主机地址：" + origin + "\t" + request.getSession().getId());
        response.setContentType("textml;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");
        filterChain.doFilter(servletRequest,servletResponse);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
