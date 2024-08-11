package com.tempaco.tempacov1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Tempacov1Application {

	public static void main(String[] args) {
		SpringApplication.run(Tempacov1Application.class, args);
	}

	@Configuration
	public class WebConfig implements WebMvcConfigurer{

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			// TODO Auto-generated method stub
			registry.addResourceHandler("/**")
			.addResourceLocations("classpath:/static/").setCachePeriod(0);
		}
		
	}
	
	
}
