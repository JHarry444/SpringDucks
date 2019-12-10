package com.example.demo.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

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
	@Test
	public void getAllDucksTest() {
		List<Duck> pond = new ArrayList<>();
		pond.add(new Duck("Ducktor Doom", "Grey", "Latveria"));
		Mockito
			.when(repo.findAll())
			.thenReturn(pond);
		assertTrue("Returned no ducks", this.service.readDucks().size() > 0);
		
		Mockito.verify(repo, times(1)).findAll();
	}
}







