package com.ssm.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(2)
@Slf4j
public class LogAspectDao {
	@Around("execution(* com.lottecard.m2l.dao..*.*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		long startAt = System.currentTimeMillis();
		JsonObject requestJson = new JsonObject();
		JsonObject responseJson = new JsonObject();
		Object[] args = pjp.getArgs();
		requestJson.addProperty("path", pjp.getSignature().getDeclaringTypeName());
		requestJson.addProperty("method", pjp.getSignature().getName());
		
		if(log.isDebugEnabled() && args != null && args.length > 0) {
			for(int i = 0; i < args.length; i++) {
				requestJson.add(args[i].getClass().getSimpleName(), new Gson().toJsonTree(args[i]));
			}
		}

		if(log.isDebugEnabled()) {
			log.debug("[STARTED DAO]:{}", requestJson.toString());
		}else if(log.isInfoEnabled()) {
			log.info("[STARTED DAO]:{}", requestJson.toString());
		}
		
		Object result = null;
		
		try {
			result = pjp.proceed();
		} finally {
			long endAt = System.currentTimeMillis();
			responseJson.addProperty("svcDaoExecutionTime", ((endAt-startAt)/1000.0) + "초");
			responseJson.addProperty("path", pjp.getSignature().getDeclaringTypeName());
			responseJson.addProperty("method", pjp.getSignature().getName());
			
			if(result != null) {		
				responseJson.add(result.getClass().getSimpleName(), new Gson().toJsonTree(result));
			}
			
			if(log.isDebugEnabled()) {
				log.debug("[COMPLETED DAO]:{}", responseJson.toString());
			}else if(log.isInfoEnabled()) {
				log.info("[COMPLETED DAO]:{}", responseJson.toString());
			}
		}
		
		return result;
	}
}
