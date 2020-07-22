package com.qa.duck.test.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.duck.dto.DuckDTO;
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

	private DuckDTO testDuck;

	private Duck testDuckWithID;

	private DuckDTO duckDTO;

	final long id = 1L;

	private ModelMapper mapper = new ModelMapper();

	private DuckDTO mapToDTO(Duck duck) {
		return this.mapper.map(duck, DuckDTO.class);
	}

	@Before
	public void init() {
		this.duckList = new ArrayList<>();
		this.testDuck = this.mapToDTO(new Duck("Ducktor Doom", "Grey", "Latveria"));

		this.testDuckWithID = new Duck(testDuck.getName(), testDuck.getColour(), testDuck.getHabitat());
		this.testDuckWithID.setId(id);

		this.duckList.add(testDuckWithID);
		this.duckDTO = this.mapToDTO(testDuckWithID);
	}

	@Test
	public void createDuckTest() {
		when(this.service.createDuck(testDuck)).thenReturn(this.duckDTO);

		assertEquals(new ResponseEntity<DuckDTO>(this.duckDTO, HttpStatus.CREATED),
				this.controller.createDuck(testDuck));

		verify(this.service, times(1)).createDuck(this.testDuck);
	}

	@Test
	public void deleteDuckTest() {
		this.controller.deleteDuck(id);

		verify(this.service, times(1)).deleteDuck(id);
	}

	@Test
	public void findDuckByIDTest() {
		when(this.service.findDuckByID(this.id)).thenReturn(this.duckDTO);

		assertEquals(new ResponseEntity<DuckDTO>(this.duckDTO, HttpStatus.OK), this.controller.getDuck(this.id));

		verify(this.service, times(1)).findDuckByID(this.id);
	}

	@Test
	public void getAllDucksTest() {

		when(service.readDucks()).thenReturn(this.duckList.stream().map(this::mapToDTO).collect(Collectors.toList()));

		assertFalse("Controller has found no ducks", this.controller.getAllDucks().getBody().isEmpty());

		verify(service, times(1)).readDucks();
	}

	@Test
	public void updateDucksTest() {
		// given
		DuckDTO newDuck = new DuckDTO(null, "Sir Duckington esq.", "Blue", "Duckington Manor");
		DuckDTO updatedDuck = new DuckDTO(this.id, newDuck.getName(), newDuck.getColour(), newDuck.getHabitat());

		when(this.service.updateDuck(newDuck, this.id)).thenReturn(updatedDuck);

		assertEquals(new ResponseEntity<DuckDTO>(updatedDuck, HttpStatus.ACCEPTED),
				this.controller.updateDuck(this.id, newDuck));

		verify(this.service, times(1)).updateDuck(newDuck, this.id);
	}

}
