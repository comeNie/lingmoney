package com.mrbt.lingmoney.mobile.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultBean;
import com.mrbt.lingmoney.utils.ResultUtils;

/**
 * @author luox
 * @Date 2017年4月24日
 */
@Component
@Aspect
public class EncodeAspect {
	/**
	 * @author syb
	 * @param pjp  
	 * @throws Throwable 抛出异常
	 * @return 返回数据列表
	 */
	@Around("execution(* *..controller*..*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {

		Object proceed = pjp.proceed();
		if (proceed instanceof PageInfo) {
			return new ResultBean(ResultUtils.getEncodeResult(proceed));
		}
		return proceed;
	}
}
