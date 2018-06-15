package com.mrbt.lingmoney.commons.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Administrator
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisOper {
	
	/**
	 * 
	 * @return	return
	 */
	OperType cmd() default OperType.NONE;

	/**
	 * 
	 * @return	return
	 */
	String key() default "";

	/**
	 * 
	 * @return	return
	 */
	String score() default "";

	/**
	 * 	
	 * @return	return
	 */
	int start() default -1;

	/**
	 * 
	 * @return	return
	 */
	int limit() default -1;
}
