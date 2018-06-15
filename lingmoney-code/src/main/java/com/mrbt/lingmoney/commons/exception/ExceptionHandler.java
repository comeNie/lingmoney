package com.mrbt.lingmoney.commons.exception;

import java.nio.file.DirectoryNotEmptyException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * json级别的session过期
 * 
 * @author lzp
 *
 */
public class ExceptionHandler implements HandlerExceptionResolver {
	Logger log = LogManager.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object arg2,
			Exception exception) {
		try {

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("ex", exception);
			log.error(exception);
			if (exception instanceof MyException) {
				// 返回登录页面
				if (exception instanceof PageSessionInvalidException) {
					return new ModelAndView("login", model);
				} else if (exception instanceof JsonSessionInvalidException) {
					// JsonSession异常
					return new ModelAndView("login", model);
				}
				// 空指针异常
			} else if (exception instanceof java.lang.NullPointerException) {
				return new ModelAndView("404", model);
				// exception.printStackTrace();
			} else if (exception instanceof ClassNotFoundException) {
				// 找不到类
				return new ModelAndView("500", model);
				// 数组下标越界
			} else if (exception instanceof ArrayIndexOutOfBoundsException) {

				return new ModelAndView("500", model);
				// 方法的参数错误
			} else if (exception instanceof IllegalArgumentException) {

				return new ModelAndView("500", model);
				// 没有访问权限
			} else if (exception instanceof IllegalAccessException) {

				return new ModelAndView("500", model);
				// 类型转换异常
			} else if (exception instanceof ClassCastException) {
				return new ModelAndView("500", model);
				// 操作数据库异常
			} else if (exception instanceof SQLException) {

				return new ModelAndView("500", model);
				// 方法未找到异常
			} else if (exception instanceof NoSuchMethodException) {

				return new ModelAndView("404", model);

				// 违法的监控状态异常
			} else if (exception instanceof IllegalStateException) {
				return new ModelAndView("500", model);
				// 系统找不到指定的路径
			} else if (exception instanceof DirectoryNotEmptyException) {
				return new ModelAndView("404", model);
				// 算数条件异常
			} else if (exception instanceof ArithmeticException) {

				return new ModelAndView("500", model);
				// 枚举类型不存在异常
			} else if (exception instanceof EnumConstantNotPresentException) {

				return new ModelAndView("500", model);
				// 索引越界异常
			} else if (exception instanceof IndexOutOfBoundsException) {
				return new ModelAndView("500", model);
				// Thread的interru搜索pt方法终止该线程时抛出该异常
			} else if (exception instanceof InterruptedException) {
				return new ModelAndView("login", model);
				// 属性不存在异常
			} else if (exception instanceof NoSuchFieldException) {

				return new ModelAndView("500", model);
			} else {
				return new ModelAndView("500", model);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
}
