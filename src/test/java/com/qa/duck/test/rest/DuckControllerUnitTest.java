package com.qa.duck.test.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.rest.DuckController;
import com.qa.duck.service.DuckService;

@RunWith(MockitoJUnitRunner.class)
public class DuckControllerUnitTest {

	@InjectMocks
	private DuckController controller;

	@Mock
	private DuckService service;

	private List<Duck> duckList;

	private Duck testDuck;

	private Duck testDuckWithID;

	final long id = 1L;
	
	@Before
	public void init() {
		this.duckList = new ArrayList<>();
		this.duckList.add(testDuck);
		this.testDuck = new Duck("Ducktor Doom", "Grey", "Latveria");
		this.testDuckWithID = new Duck(testDuck.getName(), testDuck.getColour(), testDuck.getHabitat());
		this.testDuckWithID.setId(id);
	}

	@Test
	public void createDuckTest() {
		when(this.service.createDuck(testDuck)).thenReturn(testDuckWithID);

		assertEquals(this.testDuckWithID, this.controller.createDuck(testDuck));

		verify(this.service, times(1)).createDuck(this.testDuck);
	}

	@Test
	public void deleteDuckTest() {
		this.controller.deleteDuck(id);

		verify(this.service, times(1)).deleteDuck(id);
	}

	@Test
	public void findDuckByIDTest() {
		when(this.service.findDuckByID(this.id)).thenReturn(this.testDuckWithID);

		assertEquals(this.testDuckWithID, this.controller.getDuck(this.id));

		verify(this.service, times(1)).findDuckByID(this.id);
	}

	@Test
	public void getAllDucksTest() {

		when(service.readDucks()).thenReturn(this.duckList);

		assertFalse("Controller has found no ducks", this.controller.getAllDucks().isEmpty());

		verify(service, times(1)).readDucks();
	}

	@Test
	public void updateDucksTest() {
		// given
		Duck newDuck = new Duck("Sir Duckington esq.", "Blue", "Duckington Manor");
		Duck updatedDuck = new Duck(newDuck.getName(), newDuck.getColour(), newDuck.getHabitat());
		updatedDuck.setId(this.id);

		when(this.service.updateDuck(newDuck, this.id)).thenReturn(updatedDuck);

		assertEquals(updatedDuck, this.controller.updateDuck(this.id, newDuck));

		verify(this.service, times(1)).updateDuck(newDuck, this.id);
	}

}
