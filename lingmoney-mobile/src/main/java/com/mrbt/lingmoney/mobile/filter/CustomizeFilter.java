package com.mrbt.lingmoney.mobile.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.utils.AES256Encryption;

/**
 * 过滤器[解密参数]
 * 
 * @author YJQ
 *
 */
public class CustomizeFilter implements Filter {
	
	private static final Logger LOGGER = LogManager.getLogger(CustomizeFilter.class);
	
	private FilterConfig config;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}

	/**
	 * 
	 * @param in	输入流
	 * @return	字符串
	 * @throws IOException	IOException
	 */
	public String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = "";
		try {
			HttpServletRequest hreq = (HttpServletRequest) request;
			uri = hreq.getRequestURI();
			LOGGER.info("\n----------------------------请求URL-------------------------");
			LOGGER.info(uri);

			LOGGER.info("\n----------------------------ContentType---------------------");
			LOGGER.info(hreq.getContentType());

			String param = hreq.getParameter("params");
			LOGGER.info("\n------------------------------解密前的Params--------------------");
			LOGGER.info(param);
			LOGGER.info(hreq.getMethod());
			if (uri.contains("image") || uri.contains("pictureCode") || hreq.getMethod().equals("GET")) {
				// 不做处理的控制器
				chain.doFilter(request, response);
			} else if (StringUtils.isEmpty(param)) {

				// param参数为空时，清空其他参数
				ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(hreq, null);
				chain.doFilter(wrapRequest, response);

			} else {
				// 解密
				String enParam = AES256Encryption.aes256Decrypt(param.replace(" ", "+"));

				// request中的参数转成map
				HashMap paramMap = new HashMap(JSONObject.parseObject(enParam));
				// String fullURL = hreq.getRequestURI()+"?"+hreq.getQueryString();

				// 重构request
				ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(hreq, paramMap);
				chain.doFilter(wrapRequest, response);

				
				LOGGER.info("\n----------------------------InputStream---------------------");
				InputStream i = hreq.getInputStream();
				LOGGER.info(inputStream2String(i));
				LOGGER.info("\n----------------------------ContentType---------------------");
				LOGGER.info(hreq.getContentType());

				LOGGER.info("\n------------------------------解密后的Params--------------------");
				LOGGER.info(enParam);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("报错的rul" + uri);
		}
		
	}

	@Override
	public void destroy() {
		this.config = null;
	}

}
