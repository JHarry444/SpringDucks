package com.qa.duck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;

@SpringBootApplication
public class DuckApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ApplicationContext ac= SpringApplication.run(DuckApp.class, args);
		DuckRepo dr = ac.getBean(DuckRepo.class);
		dr.save(new Duck("Ducktor Who", "blue", "TARDIS"));
	}

}
