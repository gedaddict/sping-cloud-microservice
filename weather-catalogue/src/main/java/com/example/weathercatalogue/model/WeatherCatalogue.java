package com.example.weathercatalogue.model;

import java.util.List;

public class WeatherCatalogue {

	private LocalAreaCodeDetails area;
	private LocalWeather weather;
	
	public WeatherCatalogue() {
		
	}
	
	public WeatherCatalogue(LocalAreaCodeDetails area, LocalWeather weather) {
		this.area = area;
		this.weather = weather;
	}
	
	public LocalAreaCodeDetails getArea() {
		return area;
	}

	public void setArea(LocalAreaCodeDetails area) {
		this.area = area;
	}

	public LocalWeather getWeather() {
		return weather;
	}

	public void setWeather(LocalWeather weather) {
		this.weather = weather;
	}

}
