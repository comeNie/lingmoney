package com.mrbt.lingmoney.admin.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author Administrator
 *
 */
public class SpringApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		SpringApplicationContextHolder.context = context;
	}

	/**
	 * 
	 * @param beanName
	 *            beanName
	 * @return return
	 */
	public static Object getSpringBean(String beanName) {
		/* notEmpty(beanName, "bean name is required"); */
		return context == null ? null : context.getBean(beanName);
	}

	public static String[] getBeanDefinitionNames() {
		return context.getBeanDefinitionNames();
	}
}
