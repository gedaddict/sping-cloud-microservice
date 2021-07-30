package com.example.weathercatalogue.exceptions;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler{

	private final static Logger log = LoggerFactory.getLogger(AsyncExceptionHandler.class);
	
	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		log.info("Exception occured: " +ex.getLocalizedMessage());
		log.info("in method: " +method.getName());
		for(Object obj : params) {
			log.info("params: " +obj.toString());
		}
	}

}
