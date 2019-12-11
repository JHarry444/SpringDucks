package com.example.demo.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.persistence.domain.Duck;
import com.example.demo.rest.DuckController;
import com.example.demo.service.DuckService;

@SpringBootTest
public class DuckControlUnitTests {

	@InjectMocks
	private DuckController controller;
	
	@Mock
	private DuckService service;
	
	@Test
	public void getAllDucksTest() {
		List<Duck> pond = new ArrayList<>();
		pond.add(new Duck("Ducktor Doom", "Grey", "Latveria"));
		Mockito
			.when(this.service.readDucks())
			.thenReturn(pond);
		
		assertEquals(false, this.controller.getAllDucks().isEmpty(), "Controller has found no ducks");
		
		Mockito.verify(service, times(1)).readDucks();
	}
}







