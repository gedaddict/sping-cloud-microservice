package com.example.weathercatalogue.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.weathercatalogue.model.LocalArea;
import com.example.weathercatalogue.model.LocalAreaCodeDetails;
import com.example.weathercatalogue.model.LocalWeather;
import com.example.weathercatalogue.model.WeatherCatalogue;
import com.example.weathercatalogue.service.WeatherCatalogueService;

@RestController
@RequestMapping("/weather")
public class WeatherCatalogueController {
	
	private final static Logger log = LoggerFactory.getLogger(WeatherCatalogueController.class);
	
	private final WeatherCatalogueService weatherCatalogueService;
	
	public WeatherCatalogueController (WeatherCatalogueService weatherCatalogueService) {
		this.weatherCatalogueService = weatherCatalogueService;
	}
	
	@Autowired
	private Executor taskExecutor;
	
	@Autowired
	private LocalWeather weather;
	
	@Autowired
	private LocalArea localArea;
	
	@GetMapping("/{areacode}")
	public List<WeatherCatalogue> getLocalWeatherCatalogue(@PathVariable("areacode") String areaCode) throws InterruptedException {
		log.info("Start WeatherCatalogue: " +Thread.currentThread().getName());
		//localArea = restTemplate.getForObject(localAreaCodeUrI + areaCode, LocalArea.class);
		localArea = weatherCatalogueService.getLocalArea(areaCode);
		Function<LocalAreaCodeDetails, WeatherCatalogue> weatherCatalogueBuilder = aCode -> {
			//weather = restTemplate.getForObject(localWeatherUrI + aCode.getCity(), LocalWeather.class);
			weather = weatherCatalogueService.getLocalWeather(aCode);
			return new WeatherCatalogue(aCode, weather);
			};
		Thread.sleep(3000);
		log.info("WeatherCatalogue completed: " +Thread.currentThread().getName());
		return localArea.getLocalAreaCodeDetails()
					.stream()
					.map(weatherCatalogueBuilder)
					.collect(Collectors.toList());
	}
	
	@GetMapping("/async/{areacode}")
	public CompletableFuture<ResponseEntity<List<WeatherCatalogue>>> getAsyncWeatherCatalogue(@PathVariable("areacode") String areaCode) throws InterruptedException, ExecutionException {
		log.info("Start Async: " +Thread.currentThread().getName());
		List<WeatherCatalogue> weatherCatalogue = weatherCatalogueService.getAsyncLocalArea(areaCode)
			.thenApplyAsync(localArea -> localArea.getLocalAreaCodeDetails(), taskExecutor)
			.thenComposeAsync(areaCodes -> weatherCatalogueService.assembleWeatherCatalogue(areaCodes), taskExecutor).get();
		Thread.sleep(3000);
		log.info("Async completed: " +Thread.currentThread().getName());
		return CompletableFuture.completedFuture(new ResponseEntity<List<WeatherCatalogue>>(weatherCatalogue, HttpStatus.ACCEPTED));
	}
}
