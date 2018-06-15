package com.mrbt.lingmoney.admin.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mrbt.lingmoney.model.schedule.ScheduleJob;

/**
 * 
 * @author Administrator
 *
 */
public final class TaskUtils {

	private TaskUtils() {

	}

	public static final Logger LOGGER = Logger.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 *            scheduleJob
	 */
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class<?> clazz = null;
		try {
			if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
				object = SpringUtils.getBean(scheduleJob.getSpringId());
			} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {

				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			}
			if (object == null) {
				LOGGER.error("---task [" + scheduleJob.getJobGroup() + "."
						+ scheduleJob.getJobName()
						+ "] has failed to start.Please check the configuration.---");
			}
			clazz = object.getClass();
			Method method = null;
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
			if (method != null) {
				method.invoke(object);
			}
			LOGGER.info("---task [" + scheduleJob.getJobGroup() + "."
					+ scheduleJob.getJobName() + "]  is has been excuted.---");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return;
	}
}
