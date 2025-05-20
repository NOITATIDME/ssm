package com.ssm.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ssm.cmn.utils.CmnUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(1)
@Slf4j
public class LogAspectController {
	
	@Around("execution(* com.lottecard.m2l..controller.*.*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		long startAt = System.currentTimeMillis();
		JsonObject requestJson = new JsonObject();
		JsonObject responseJson = new JsonObject();
		Object[] args = pjp.getArgs();
		
		if(log.isDebugEnabled() && args != null && args.length > 0) {
			for(int i = 0; i < args.length; i++) {
				if(args[i] instanceof HttpServletRequest) {
					HttpServletRequest request = (HttpServletRequest) args[i];
					if(!CmnUtils.isEmpty(request.getHeader("Token"))) {
						requestJson.addProperty("Token", request.getHeader("Token"));
					}
				}else if(args[i] instanceof HttpServletResponse) {
					continue;
				}else {
					requestJson.add(args[i].getClass().getSimpleName(), new Gson().toJsonTree(args[i]));
				}
			}
		}
		
		requestJson.addProperty("path", pjp.getSignature().getDeclaringTypeName());
		requestJson.addProperty("method", pjp.getSignature().getName());
		
		if(log.isDebugEnabled()) {
			log.debug("[STARTED CONTROLLER]:{}", requestJson.toString());
		}else if(log.isInfoEnabled()) {
			log.info("[STARTED CONTROLLER]:{}", requestJson.toString());
		}
		
		Object result = null;
		
		try {
			result = pjp.proceed();
		} finally {
			long endAt = System.currentTimeMillis();
			responseJson.addProperty("svcControllerExecutionTime", ((endAt-startAt)/1000.0) + "초");
			responseJson.addProperty("path", pjp.getSignature().getDeclaringTypeName());
			responseJson.addProperty("method", pjp.getSignature().getName());
			
			if(result != null) {
//				if(result instanceof CmnOutDto) {
//					String now = DateUtils.getStringDate(new Date(), LocaDateFormat.Dtti.getFormat());
//					((cmnOutDto)result).setTimeStamp(now);
//				}
				if(log.isDebugEnabled()) {
					responseJson.add(result.getClass().getSimpleName(), new Gson().toJsonTree(result));
				}
			}
		
			if(log.isDebugEnabled()) {
				log.debug("[COMPLETED CONTROLLER]:{}", responseJson.toString());
			}else if(log.isInfoEnabled()) {
				log.info("[COMPLETED CONTROLLER]:{}", responseJson.toString());
			}
			
		}
		
		return result;
		
	}
}
