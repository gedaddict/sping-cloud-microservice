package com.example.weatherservice.model;

public class LocalArea {

	private String areaCode;
	private String name;
	
	public LocalArea() {
		
	}
	
	public LocalArea(String areaCode, String name) {
		this.areaCode = areaCode;
		this.name = name;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Area [areaCode=" + areaCode + ", name=" + name + "]";
	}
	
}
