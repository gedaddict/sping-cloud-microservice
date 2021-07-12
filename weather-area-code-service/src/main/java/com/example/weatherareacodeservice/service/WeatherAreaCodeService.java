package com.example.weatherareacodeservice.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.PUT;

import org.springframework.stereotype.Service;

import com.example.weatherareacodeservice.model.LocalArea;

@Service
public class WeatherAreaCodeService {
	
	@Singleton
	private Map<String, String> areaCodes = new HashMap<String, String>() {{
		put("1234", "London");
		put("1111", "New York");
		put("2222", "Tokyo");
		put("3333", "Milan");
		put("4444", "Sydney");
	}};
	
	public String getLocalArea(String areaCode) {
		
		return areaCodes.get(areaCode);
	}
}
