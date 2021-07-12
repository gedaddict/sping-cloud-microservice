package com.example.weathercatalogue.model;

import java.util.List;

public class LocalArea {
	
	private List<LocalAreaCodeDetails> localAreaCodeDetails;
	
	public LocalArea() {
		
	}
	
	public LocalArea(List<LocalAreaCodeDetails> a) {
		this.localAreaCodeDetails = a;
	}

	public List<LocalAreaCodeDetails> getLocalAreaCodeDetails() {
		return localAreaCodeDetails;
	}

	public void setLocalAreaCodeDetails(List<LocalAreaCodeDetails> localAreaCodeDetails) {
		this.localAreaCodeDetails = localAreaCodeDetails;
	}

	
}
