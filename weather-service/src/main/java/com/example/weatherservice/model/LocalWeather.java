package com.example.weatherservice.model;

import java.util.List;
import java.util.TimeZone;

import javax.print.attribute.standard.DateTimeAtCreation;

public class LocalWeather {

	private Coord coord;
	private List<Weather> weather;
	private String base;
	private Main main;
	private int visibility;
	private Wind wind;
	private Clouds clouds;
	private int dt;
	private Sys sys;
	private int timezone;
	private int id;
	private String name;
	private String cod;
	
	public LocalWeather() {
		
	}
	
	//public LocalWeather(Coord coord, List<Weather> weather, String base, Main main, int visibility, Wind wind, Clouds clouds, int dt) {
	public LocalWeather(String a) {
		this.coord = null;
		this.weather = null;
		this.base = null;
		this.main = null;
		this.visibility = 0;
		this.wind = null;
		this.clouds = null;
		this.dt = 0;
		this.sys = null;
		this.timezone = 0;
		this.id = 0;
		this.name = null;
		this.cod = null;
	}

	public List<Weather> getWeather() {
		return weather;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public Wind getWind() {
		return wind;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public int getDt() {
		return dt;
	}

	public void setDt(int dt) {
		this.dt = dt;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}
	
	
	
	
}
