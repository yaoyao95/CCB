
package com.CantoneseClubBBS.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 日志切面类，用于记录方法的性能和异常情况
 * 
 * @author AoiHoshino
 * @email AoiHoshino999@gmail.com
 * @date 2017/04/20
 * @version 1.0
 * @update Date
 */
public class LogAdvice {

	private Logger logger = Logger.getLogger(LogAdvice.class);

	/**
	 * 在业务层方法的前，后织入 记录方法运行时间
	 * 
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("开始调用[ " + pjp.getSignature().getName() + " 方法]");
		/** 开始调用方法的时间毫秒数 */
		long beginMillSeconds = System.currentTimeMillis();
		/** 调用目标方法 */
		Object res = pjp.proceed(pjp.getArgs());
		/** 记录结束调用的时间毫秒数 */
		long endMillSeconds = System.currentTimeMillis();
		logger.info("结束调用方法[ " + pjp.getSignature().getName() + " ] 耗时：" + (endMillSeconds - beginMillSeconds) + " 毫秒");
		return res;
	}

	/** 记录方法错误信息 */
	public void error(JoinPoint jp, Throwable t) {
		logger.error("调用方法 [ " + jp.getSignature().getName() + " ] 时出现异常");
		logger.error(t.getMessage(), t);
	}
}
