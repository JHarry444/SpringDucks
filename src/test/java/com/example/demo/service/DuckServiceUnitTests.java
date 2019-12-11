package com.example.demo.service;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.persistence.domain.Duck;
import com.example.demo.persistence.repo.DuckRepo;

@RunWith(SpringRunner.class)
public class DuckServiceUnitTests {

	@InjectMocks
	private DuckService service;

	@Mock
	private DuckRepo repo;
	
	private List<Duck> duckList;
	
	private final Duck TEST_DUCK = new Duck("Ducktor Doom", "Grey", "Latveria");
	
	
	@Before
	public void setup() {
		List<Duck> pond = new ArrayList<>();
		pond.add(TEST_DUCK);
	}

	@Test
	public void getAllDucksTest() {
		
		Mockito.when(repo.findAll()).thenReturn(this.duckList);

		assertFalse("Controller has found no ducks", this.service.readDucks().isEmpty());

		Mockito.verify(repo, times(1)).findAll();
	}
	
	
	public void deleteDuckTest() {
		this.service.deleteDuck(1L);
		
		verify(this.service, times(1)).deleteDuck(1L);
	}
	
	
	
	
	
	
	
}
