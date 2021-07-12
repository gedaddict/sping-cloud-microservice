package com.example.weatherareacodeservice.model;

import java.util.List;

public class LocalArea {

	private List<LocalAreaCodeDetails> localAreaCodeDetails;
	
	public LocalArea() {
		
	}
	
	public LocalArea(String a) {
		this.localAreaCodeDetails = null;
	}

	public List<LocalAreaCodeDetails> getLocalAreaCodeDetails() {
		return localAreaCodeDetails;
	}

	public void setLocalAreaCodeDetails(List<LocalAreaCodeDetails> localAreaCodeDetails) {
		this.localAreaCodeDetails = localAreaCodeDetails;
	}

	
}
