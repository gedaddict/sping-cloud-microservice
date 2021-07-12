package com.example.weatherservice.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.weatherservice.model.LocalWeather;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/localweather")
public class WeatherServiceController {
	
	private final static Logger log = LoggerFactory.getLogger(WeatherServiceController.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${my_app_weather.services.localweather}")
	private String localWeatherUri;
	
	@Value("${my_app_weather.services.apiKey}")
	private String apiKey;
	
	@GetMapping("/{localArea}")
	//@CircuitBreaker(fallbackMethod = "getLocalWeatherFallback", name = "LocalWeatherService")
	public LocalWeather getLocalWeather(@PathVariable("localArea") String localArea) {
		log.info("accessing weather API for " + "[ " + localArea + " ]" + "...");
		return restTemplate.getForObject(localWeatherUri + localArea + "&appid=" + apiKey, LocalWeather.class);
	}
	
	public LocalWeather getLocalWeatherFallback(@PathVariable("localArea") String localArea) {
		log.info("executing Circuit breaker: weather API currently not available...");
		return new LocalWeather(localArea);
	}
}
