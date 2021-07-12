package com.example.weathercatalogue.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.weathercatalogue.model.LocalArea;
import com.example.weathercatalogue.model.LocalAreaCodeDetails;
import com.example.weathercatalogue.model.LocalWeather;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class WeatherCatalogueService {
	
	private final static Logger log = LoggerFactory.getLogger(WeatherCatalogueService.class);
	
	private final static String WEATHER_SERVICE = "weather-service";
	private final static String WEATHER_AREA_CODE_SERVICE = "weather-area-code-service";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LocalWeather localWeather;
	
	@Autowired
	private LocalArea localArea;
	
	@Autowired
	private List<LocalAreaCodeDetails> defaultLocalAreaDetails;

	@CircuitBreaker(name = WEATHER_SERVICE, fallbackMethod = "getLocalWeatherFallback")
	public LocalWeather getLocalWeather(String localWeatherUrI, LocalAreaCodeDetails aCode) {
		log.info("connecting to [ weather-service ]...");
		localWeather = restTemplate.getForObject(localWeatherUrI + aCode.getCity(), LocalWeather.class);
		return localWeather;
	}
	
	public LocalWeather getLocalWeatherFallback(Exception e) {
		log.info("executing getLocalWeatherFallback Circuit breaker: [ weather-service ] currently unavailable...");
		return new LocalWeather("currently unavailable");
	}
	
	@CircuitBreaker(name = WEATHER_AREA_CODE_SERVICE, fallbackMethod = "getLocalAreaFallback")
	public LocalArea getLocalArea(String localAreaCodeUrI, String areaCode) {
		log.info("connecting to [ weather-area-code-service ]...");
		localArea = restTemplate.getForObject(localAreaCodeUrI + areaCode, LocalArea.class);
		return localArea;
	}
	
	public LocalArea getLocalAreaFallback(Exception e) {
		log.info("executing getLocalAreaFallback Circuit breaker: [ weather-area-code-service ] currently unavailable...");
		log.info("loading default local area code: [ PH ] - [ 1004 ] ");
		defaultLocalAreaDetails.add(new LocalAreaCodeDetails("currently unavailable"));
		localArea.setLocalAreaCodeDetails(defaultLocalAreaDetails);
		return localArea;
	}
}
