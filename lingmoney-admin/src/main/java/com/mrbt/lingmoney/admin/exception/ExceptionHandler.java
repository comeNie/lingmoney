package com.mrbt.lingmoney.admin.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * json级别的session过期
 * 
 * @author lzp
 *
 */
public class ExceptionHandler implements HandlerExceptionResolver {
	Logger log = MyUtils.getLogger(ExceptionHandler.class);

	/**
	 * @param request	 request
	 * @param response	response
	 * @param arg2	arg2
	 * @param exception exception
	 * @return null
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception exception) {
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			if (exception instanceof PageSessionInvalidException) {
				writer.write("<script>window.top.location.href='/index.html'</script>");
			} else if (exception instanceof JsonSessionInvalidException) {
				ObjectMapper mapper = new ObjectMapper();
				PageInfo pageInfo = new PageInfo();
				pageInfo.setCode(ResultInfo.LOGIN_FAILURE.getCode());
				pageInfo.setMsg("用户超时，请重新登录");
				writer.write(mapper.writeValueAsString(pageInfo));
			} else {
				ObjectMapper mapper = new ObjectMapper();
				PageInfo pageInfo = new PageInfo();
				pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
				pageInfo.setMsg(exception.getMessage());
				writer.write(mapper.writeValueAsString(pageInfo));
			}
		} catch (IOException e) {
			log.error(e);
		}
		return null;
	}
}
