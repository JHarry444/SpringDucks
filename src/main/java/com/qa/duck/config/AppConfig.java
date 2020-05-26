package com.qa.duck.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "prod"})
public class AppConfig {

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
}
