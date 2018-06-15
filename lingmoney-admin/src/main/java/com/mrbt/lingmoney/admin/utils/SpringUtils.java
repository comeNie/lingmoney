package com.mrbt.lingmoney.admin.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Spring 工具类
 * 
 * @author yjq
 *
 */
@Component
public final class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext context;

	private SpringUtils() {
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		SpringUtils.context = context;
	}

	/**
	 * @param clazz
	 *            clazz
	 * @param <T>
	 *            <T>
	 * @return return
	 */
	public static <T> T getBean(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		return context.getBean(clazz);
	}

	/**
	 * 
	 * @param beanName
	 *            beanName
	 * @param clazz
	 *            clazz
	 * @param <T>
	 *            <T>
	 * @return return
	 */
	public static <T> T getBean(String beanName, Class<T> clazz) {
		if (null == beanName || "".equals(beanName.trim())) {
			return null;
		}
		if (clazz == null) {
			return null;
		}
		return (T) context.getBean(beanName, clazz);
	}

	/**
	 * by yjq 2016.12.20 for scheduled
	 * 
	 * @param beanName	beanName
	 * @param <T>
	 *            <T>
	 * @return	return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		if (null == beanName || "".equals(beanName.trim())) {
			return null;
		}
		return (T) context.getBean(beanName);
	}

	/**
	 * 
	 * @return	return
	 */
	public static ApplicationContext getContext() {
		if (context == null) {
			return null;
		}
		return context;
	}

	/**
	 * 
	 * @param event	
	 */
	public void publishEvent(ApplicationEvent event) {
		if (context == null) {
			return;
		}
		context.publishEvent(event);
	}

}
