package com.example.weatherareacodeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
//@ComponentScan(basePackages = "com.example.weatherareacodeservice.configuration")
public class WeatherAreaCodeServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WeatherAreaCodeServiceApplication.class, args);
	}

}
