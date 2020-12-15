package com.qa.duck.test.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.rest.DuckController;
import com.qa.duck.service.DuckService;

@SpringBootTest
class DuckControllerUnitTest {

	@Autowired
	private DuckController controller;

	@MockBean
	private DuckService service;

	private List<Duck> duckList;

	private DuckDTO testDuck;

	private Duck testDuckWithID;

	private DuckDTO duckDTO;

	private final long id = 1L;

	private ModelMapper mapper = new ModelMapper();

	private DuckDTO mapToDTO(Duck duck) {
		return this.mapper.map(duck, DuckDTO.class);
	}

	@BeforeEach
	void init() {
		this.duckList = new ArrayList<>();
		this.testDuck = this.mapToDTO(new Duck("Ducktor Doom", "Grey", "Latveria"));

		this.testDuckWithID = new Duck(testDuck.getName(), testDuck.getColour(), testDuck.getHabitat());
		this.testDuckWithID.setId(id);

		this.duckList.add(testDuckWithID);
		this.duckDTO = this.mapToDTO(testDuckWithID);
	}

	@Test
	void createDuckTest() {
		when(this.service.createDuck(testDuck)).thenReturn(this.duckDTO);

		assertThat(new ResponseEntity<DuckDTO>(this.duckDTO, HttpStatus.CREATED))
				.isEqualTo(this.controller.createDuck(testDuck));

		verify(this.service, times(1)).createDuck(this.testDuck);
	}

	@Test
	void deleteDuckTest() {
		this.controller.deleteDuck(id);

		verify(this.service, times(1)).deleteDuck(id);
	}

	@Test
	void findDuckByIDTest() {
		when(this.service.findDuckByID(this.id)).thenReturn(this.duckDTO);

		assertThat(new ResponseEntity<DuckDTO>(this.duckDTO, HttpStatus.OK))
				.isEqualTo(this.controller.getDuck(this.id));

		verify(this.service, times(1)).findDuckByID(this.id);
	}

	@Test
	void getAllDucksTest() {

		when(service.readDucks()).thenReturn(this.duckList.stream().map(this::mapToDTO).collect(Collectors.toList()));

		assertThat(this.controller.getAllDucks().getBody().isEmpty()).isFalse();

		verify(service, times(1)).readDucks();
	}

	@Test
	void updateDucksTest() {
		// given
		DuckDTO newDuck = new DuckDTO(null, "Sir Duckington esq.", 25, "Blue", "Duckington Manor");
		DuckDTO updatedDuck = new DuckDTO(this.id, newDuck.getName(), 25, newDuck.getColour(), newDuck.getHabitat());

		when(this.service.updateDuck(newDuck, this.id)).thenReturn(updatedDuck);

		assertThat(new ResponseEntity<DuckDTO>(updatedDuck, HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.updateDuck(this.id, newDuck));

		verify(this.service, times(1)).updateDuck(newDuck, this.id);
	}

}
