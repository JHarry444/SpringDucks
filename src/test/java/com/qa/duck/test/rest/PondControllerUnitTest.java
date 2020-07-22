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
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.rest.DuckController;
import com.qa.duck.service.DuckService;
import com.qa.duck.service.Mapper;

@RunWith(MockitoJUnitRunner.class)
public class PondControllerUnitTest {

	@InjectMocks
	private DuckController controller;

	@Mock
	private DuckService service;

	private List<DuckDTO> duckList;

	private Duck testDuck;

	private Duck testDuckWithID;

	private final long ID = 1L;

	private Mapper<Duck, DuckDTO> mapper = (duck) -> new ModelMapper().map(duck, DuckDTO.class);

	private DuckDTO testDuckDTO;

	@Before
	public void init() {
		this.testDuck = new Duck("Ducktor Doom", "Grey", "Latveria");
		this.testDuckWithID = new Duck(testDuck.getName(), testDuck.getColour(), testDuck.getHabitat());
		this.testDuckWithID.setId(ID);
		this.testDuckDTO = this.mapper.mapToDTO(testDuckWithID);
		this.duckList = new ArrayList<>();
		this.duckList.add(testDuckDTO);
	}

	@Test
	public void createDuckTest() {
		when(this.service.createDuck(testDuck)).thenReturn(this.testDuckDTO);

		assertEquals(new ResponseEntity<DuckDTO>(this.testDuckDTO, HttpStatus.CREATED),
				this.controller.createDuck(testDuck));

		verify(this.service, times(1)).createDuck(this.testDuck);
	}

	@Test
	public void deleteDuckTest() {
		this.controller.deleteDuck(ID);

		verify(this.service, times(1)).deleteDuck(ID);
	}

	@Test
	public void findDuckByIDTest() {
		when(this.service.findDuckByID(this.ID)).thenReturn(this.testDuckDTO);

		assertEquals(new ResponseEntity<DuckDTO>(this.testDuckDTO, HttpStatus.OK), this.controller.getDuck(this.ID));

		verify(this.service, times(1)).findDuckByID(this.ID);
	}

	@Test
	public void getAllDucksTest() {

		when(service.readDucks()).thenReturn(this.duckList);

		assertFalse("Controller has found no ducks", this.controller.getAllDucks().getBody().isEmpty());

		verify(service, times(1)).readDucks();
	}

	@Test
	public void updateDucksTest() {
		// given
		Duck newDuck = new Duck("Sir Duckington esq.", "Blue", "Duckington Manor");
		Duck updatedDuck = new Duck(newDuck.getName(), newDuck.getColour(), newDuck.getHabitat());
		updatedDuck.setId(this.ID);
		DuckDTO updatedDuckDTO = this.mapper.mapToDTO(updatedDuck);
		when(this.service.updateDuck(newDuck, this.ID)).thenReturn(updatedDuckDTO);

		assertEquals(new ResponseEntity<DuckDTO>(updatedDuckDTO, HttpStatus.ACCEPTED),
				this.controller.updateDuck(this.ID, newDuck));

		verify(this.service, times(1)).updateDuck(newDuck, this.ID);
	}

}
