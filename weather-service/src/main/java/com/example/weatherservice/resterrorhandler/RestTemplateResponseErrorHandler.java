package com.example.weatherservice.resterrorhandler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.example.weatherservice.controller.WeatherServiceController;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
	
	private final static Logger log = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		
		return (response.getStatusCode().series() ==  HttpStatus.Series.CLIENT_ERROR
				|| response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().series() ==  HttpStatus.Series.SERVER_ERROR) {
			log.info("HttpStatus.Series.SERVER_ERROR: " + response.getStatusText() + " [ " + response.getHeaders().getFirst("X-Cache-Key") + " ]");
		} else if (response.getStatusCode().series() ==  HttpStatus.Series.CLIENT_ERROR) {
			log.info("HttpStatus.Series.CLIENT_ERROR: " + response.getStatusText() + " [ " + response.getHeaders().getFirst("X-Cache-Key") + " ]");
		}
		
	}

}
