package com.qa.duck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;
import com.qa.duck.service.DuckService;

@SpringBootApplication
public class DuckApp {

	public static void main(String[] args) {
		ApplicationContext ac= SpringApplication.run(DuckApp.class, args);
		DuckRepo dr = ac.getBean(DuckRepo.class);
		dr.save(new Duck("Ducktor Who", "blue", "TARDIS"));
	}

}
