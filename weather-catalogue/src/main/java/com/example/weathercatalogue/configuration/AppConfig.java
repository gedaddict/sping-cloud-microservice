package com.example.weathercatalogue.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.example.weathercatalogue.exceptions.AsyncExceptionHandler;
import com.example.weathercatalogue.model.LocalArea;
import com.example.weathercatalogue.model.LocalAreaCodeDetails;
import com.example.weathercatalogue.model.LocalWeather;

@Configuration
public class AppConfig extends AsyncConfigurerSupport{
	
	@Autowired
	private AsyncExceptionHandler asyncExceptionHandler;

	@Bean(name = "taskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(2); 
	    executor.setMaxPoolSize(2);
	    executor.setQueueCapacity(500);
	    executor.setThreadNamePrefix("taskExecutor-");
	    executor.initialize();
	    return executor;
	}
	
	@Bean
	public AsyncExceptionHandler getAsyncUncaughtExceptionHandler() {
		return asyncExceptionHandler;
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public LocalWeather getLocalWeather() {
		return new LocalWeather();
	}
	
	@Bean
	public LocalArea getLocalArea() {
		return new LocalArea();
	}
	
	@Bean
	public List<LocalAreaCodeDetails> getLocalAreaCodeDetails() {
		return new ArrayList<LocalAreaCodeDetails>();
	}
}
