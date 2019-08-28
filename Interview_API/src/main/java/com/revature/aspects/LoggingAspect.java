package com.revature.aspects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
public class LoggingAspect {
	private Logger logger = Logger.getRootLogger();
	
	@Around("execution(* com.revature.services.*.*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		String logInfo = "";
		if (pjp.getSignature().getName() != null) {
			logInfo = logInfo + "Method: " + pjp.getSignature().getName() + "\n";
		} else {
			logInfo = logInfo + "Method not found\n";
		}
		Object value = pjp.proceed();
		try {
			logInfo = logInfo + "Result: " + value.toString() + "\n";
			logger.info(logInfo);
		}catch (NullPointerException e) {
			logger.warn(e);
		}
		
		if (value != null) {
			logInfo = logInfo + "Result: " + value.toString() + "\n";
		} else {
			logInfo = logInfo + "Result: method returned null\n";
		}
		logger.info(logInfo);
		return value;
	}
	
	@Around("execution(* com.revature.controllers.*.*(..))")
	public Object logging2(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String logInfo = "";
		if (request.getRemoteAddr().equals("") || request.getRemoteAddr() == null) {
			logInfo = logInfo + "IP Address not found!\n";
		} else {
			logInfo = logInfo + "IP Address: " + request.getRemoteAddr() + "\n";
		}
		logger.info(logInfo);
		return pjp.proceed();
	}
}
