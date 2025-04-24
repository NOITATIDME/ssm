package com.ssm.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.slf4j.MDC;

import com.google.gson.JsonObject;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MdcFilter implements Filter { 
	
	// MDC(Mapped Diagnostic Context)는 현재 실행중인 쓰레드에 메타 정보를 넣고 관리하는 공간
	// 

	private static final String TRACE_ID = "traceId";
	
	private static final Set<String> excludePathSet = Collections.unmodifiableSet(new HashSet<String>(
			Arrays.asList("/swagger-ui/**","bbbb","cccc"))); // filter를 제외 시킬 uri 추가 ex) swagger
	
	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		JsonObject requestJson = new JsonObject();
		JsonObject responseJson = new JsonObject();
		
		if(!shouldNotFilter(httpServletRequest)) {
			String traceId = UUID.randomUUID().toString();
			MDC.put(TRACE_ID, traceId);
			httpServletResponse.setHeader("Trace-Id", traceId);
			
			requestJson.addProperty("requestUri", httpServletRequest.getRequestURI());
			log.info("[STARTED FILTER]:{}", requestJson.toString());
			
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			
			responseJson.addProperty("status", httpServletResponse.getStatus());
			log.info("[COMPLETED FILTER]:{}", responseJson.toString());
			MDC.clear();
		}else {
			requestJson.addProperty("requestUri", httpServletRequest.getRequestURI());
			log.info("[STARTED FILTER]:{}", requestJson.toString());

			filterChain.doFilter(httpServletRequest, httpServletResponse);

			responseJson.addProperty("status", httpServletResponse.getStatus());
			log.info("[COMPLETED FILTER]:{}", requestJson.toString());
			
		}
	}
	
	
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		
		for(String pattern: excludePathSet) {
			if(path.contains(pattern)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void destroy() {
		Filter.super.destroy();
	}
}
