package com.example.weathercatalogue.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.weathercatalogue.model.LocalArea;
import com.example.weathercatalogue.model.LocalAreaCodeDetails;
import com.example.weathercatalogue.model.LocalWeather;
import com.example.weathercatalogue.model.WeatherCatalogue;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class WeatherCatalogueService {
	
	private final static Logger log = LoggerFactory.getLogger(WeatherCatalogueService.class);
	
	private final static String WEATHER_SERVICE = "weather-service";
	private final static String WEATHER_AREA_CODE_SERVICE = "weather-area-code-service";
	private final RestTemplate restTemplate;
	
	public WeatherCatalogueService (RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Autowired
	private LocalWeather localWeather;
	
	@Autowired
	private LocalArea localArea;
	
	@Autowired
	private List<LocalAreaCodeDetails> defaultLocalAreaDetails;
	
	@Value("${my_app_weather.services.areacode}")
	private String localAreaCodeUrI;
	
	@Value("${my_app_weather.services.localweather}")
	private String localWeatherUrI;

	@CircuitBreaker(name = WEATHER_SERVICE, fallbackMethod = "getLocalWeatherFallback")
	public LocalWeather getLocalWeather(LocalAreaCodeDetails aCode) {
		log.info("connecting to [ weather-service ]...");
		localWeather = restTemplate.getForObject(localWeatherUrI + aCode.getCity(), LocalWeather.class);
		return localWeather;
	}
	
	public LocalWeather getLocalWeatherFallback(Exception e) {
		log.info("executing getLocalWeatherFallback Circuit breaker: [ weather-service ] currently unavailable...");
		return new LocalWeather("currently unavailable");
	}
	
	@CircuitBreaker(name = WEATHER_AREA_CODE_SERVICE, fallbackMethod = "getLocalAreaFallback")
	public LocalArea getLocalArea(String areaCode) {
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
	
	@Async
	public CompletableFuture<LocalArea> getAsyncLocalArea(String areaCode) throws InterruptedException {
		log.info("executing Async [ weather-area-code-service ]... " +Thread.currentThread().getName());
		Thread.sleep(3000);
		localArea = restTemplate.getForObject(localAreaCodeUrI + areaCode, LocalArea.class);
		return CompletableFuture.completedFuture(localArea);
	}
	
	@Async
	public CompletableFuture<LocalWeather> getAsyncLocalWeather(LocalAreaCodeDetails aCode) throws InterruptedException {
		log.info("executing Async to [ weather-service ]... " +Thread.currentThread().getName());
		localWeather = restTemplate.getForObject(localWeatherUrI + aCode.getCity(), LocalWeather.class);
		Thread.sleep(3000);
		return CompletableFuture.completedFuture(localWeather);
	}
	
	@Async
	public CompletableFuture<List<WeatherCatalogue>> assembleWeatherCatalogue(List<LocalAreaCodeDetails> areaCodes) {
		log.info("assemble Weather Catalogue " +Thread.currentThread().getName());
		
		Function<LocalAreaCodeDetails, WeatherCatalogue> assemble = areaCode -> {
			try {
				localWeather = getAsyncLocalWeather(areaCode).get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return new WeatherCatalogue(areaCode, localWeather);
		};
		
		return CompletableFuture.completedFuture(
				areaCodes.parallelStream()
							.map(assemble)
							.collect(Collectors.toList()));
	
	}
}
