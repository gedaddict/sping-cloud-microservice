package com.example.weatherareacodeservice.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.weatherareacodeservice.model.LocalArea;
import com.example.weatherareacodeservice.model.LocalAreaCodeDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/areacode")
public class WeatherAreaCodeServiceController {
	
	private final static Logger log = LoggerFactory.getLogger(WeatherAreaCodeServiceController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpEntity<String> entity;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private LocalArea localArea;
	
	@Value("${my_app_weather.services.localAreaCode}")
	private String localAreaCodeUri;
	
	@Value("${my_app_weather.services.apiKey}")
	private String apiKey;
	
	@Value("${my_app_weather.services.countryCode:}")
	private String countryCode;

	@GetMapping("{areacode}")
	@CircuitBreaker(name = "LocalArea", fallbackMethod = "getAreaFallback")
	public LocalArea getArea(@PathVariable("areacode") String areaCode) {
		log.info("accessing areacode API for " + "[ " + areaCode + " ]" + " in " + "[ " + countryCode + " ]"  +  "...");
		ResponseEntity<Object> exchange = restTemplate.exchange(localAreaCodeUri + apiKey +"&codes="+areaCode+"&country="+countryCode,HttpMethod.GET,entity,Object.class);
		Map<String, Object> map = (Map<String, Object>) exchange.getBody();
		Map<Object, Object> object = (Map<Object, Object>) map.get("results");
		List<Map<String, LocalAreaCodeDetails>> areaCodes = (List<Map<String, LocalAreaCodeDetails>>) object.get(areaCode);
		
		List<LocalAreaCodeDetails> collect = areaCodes.stream()
												.map(aCode -> objectMapper.convertValue(aCode, LocalAreaCodeDetails.class))
												.collect(Collectors.toList());
		
		localArea.setLocalAreaCodeDetails(collect);
		return localArea;
	}
	
	public LocalArea getAreaFallback(@PathVariable("areacode") String areaCode) {
		log.info("executing Circuit breaker: areaCode API currently not available...");
		return new LocalArea(areaCode);
	}
}
