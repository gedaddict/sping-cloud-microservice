package com.example.weathercatalogue.controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Autowired
	private WeatherCatalogueService weatherCatalogueService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LocalWeather weather;
	
	@Autowired
	private LocalArea localArea;
	
	@Value("${my_app_weather.services.areacode}")
	private String localAreaCodeUrI;
	
	@Value("${my_app_weather.services.localweather}")
	private String localWeatherUrI;
	
	@Value("${my_app_weather.services.apiKey}")
	private String apiKey;
	
	@GetMapping("/{areacode}")
	public List<WeatherCatalogue> getLocalWeather(@PathVariable("areacode") String areaCode) {
		
		Function<LocalAreaCodeDetails, WeatherCatalogue> weatherCatalogueBuilder = aCode -> {
						//weather = restTemplate.getForObject(localWeatherUrI + aCode.getCity(), LocalWeather.class);
						weather = weatherCatalogueService.getLocalWeather(localWeatherUrI, aCode);
						return new WeatherCatalogue(aCode, weather);
						};
						
		//localArea = restTemplate.getForObject(localAreaCodeUrI + areaCode, LocalArea.class);
		localArea = weatherCatalogueService.getLocalArea(localAreaCodeUrI, areaCode);
		
		return localArea.getLocalAreaCodeDetails()
							.stream()
							.map(weatherCatalogueBuilder)
							.collect(Collectors.toList());
	}
}
