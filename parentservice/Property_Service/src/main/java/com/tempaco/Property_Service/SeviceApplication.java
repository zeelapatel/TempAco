package com.tempaco.Property_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.tempaco.authentication.model", "com.tempaco.Property_Service.entity"})
@ComponentScan(basePackages = {"com.tempaco"})
public class SeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeviceApplication.class, args);
	}

}
