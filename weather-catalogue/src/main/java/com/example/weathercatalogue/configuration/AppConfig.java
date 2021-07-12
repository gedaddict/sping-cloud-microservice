package com.example.weathercatalogue.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.weathercatalogue.model.LocalArea;
import com.example.weathercatalogue.model.LocalAreaCodeDetails;
import com.example.weathercatalogue.model.LocalWeather;

@Configuration
public class AppConfig {

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
