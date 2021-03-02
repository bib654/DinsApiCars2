package com.dins.Cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CarsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarsApplication.class, args);
	}

}
