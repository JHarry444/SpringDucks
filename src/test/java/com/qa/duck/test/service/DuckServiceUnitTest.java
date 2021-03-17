package com.qa.duck.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;
import com.qa.duck.service.DuckService;

@SpringBootTest
class DuckServiceUnitTest {

	@Autowired
	private DuckService service;

	@MockBean
	private DuckRepo repo;

	@MockBean
	private ModelMapper mapper;

	private List<Duck> duckList;

	private Duck testDuck;

	private Duck testDuckWithID;

	private DuckDTO duckDTO;

	private final long ID = 1L;

	@BeforeEach
	void init() {
		this.duckList = new ArrayList<>();
		this.duckList.add(testDuck);
		this.testDuck = new Duck("Ducktor Doom", "Grey", "Latveria");
		this.testDuckWithID = new Duck(testDuck.getName(), testDuck.getColour(), testDuck.getHabitat());
		this.testDuckWithID.setId(ID);
		this.duckDTO = new ModelMapper().map(testDuckWithID, DuckDTO.class);
	}

	@Test
	void createDuckTest() {
		DuckDTO newDuckDTO = new DuckDTO(null, "Ducktor Doom", 25, "Grey", "Latveria");

		when(this.mapper.map(newDuckDTO, Duck.class)).thenReturn(testDuck);
		when(this.repo.save(testDuck)).thenReturn(testDuckWithID);
		when(this.mapper.map(testDuckWithID, DuckDTO.class)).thenReturn(duckDTO);

		assertThat(this.duckDTO).isEqualTo(this.service.createDuck(newDuckDTO));

		verify(this.repo, times(1)).save(this.testDuck);
	}

	@Test
	void deleteDuckTest() {
		when(this.repo.existsById(ID)).thenReturn(true, false);

		assertThat(this.service.deleteDuck(ID)).isTrue();

		verify(this.repo, times(1)).deleteById(ID);
		verify(this.repo, times(2)).existsById(ID);
	}

	@Test
	void findDuckByIDTest() {
		when(this.repo.findById(this.ID)).thenReturn(Optional.of(this.testDuckWithID));
		when(this.mapper.map(testDuckWithID, DuckDTO.class)).thenReturn(duckDTO);

		assertThat(this.duckDTO).isEqualTo(this.service.findDuckByID(this.ID));

		verify(this.repo, times(1)).findById(this.ID);
	}

	@Test
	void readDucksTest() {

		when(repo.findAll()).thenReturn(this.duckList);
		when(this.mapper.map(testDuckWithID, DuckDTO.class)).thenReturn(duckDTO);

		assertThat(this.service.readDucks().isEmpty()).isFalse();

		verify(repo, times(1)).findAll();
	}

	@Test
	void updateDucksTest() {
		// given
		final long ID = 1L;
		DuckDTO newDuck = new DuckDTO(null, "Daffy", 25, "Black", "WB Studios");
		Duck duck = new Duck("Donald", "White", "Disney World");
		duck.setAge(25);
		duck.setId(ID);
		Duck updatedDuck = new Duck(newDuck.getName(), newDuck.getColour(), newDuck.getHabitat());
		updatedDuck.setAge(25);
		updatedDuck.setId(ID);
		DuckDTO updatedDTO = new DuckDTO(ID, updatedDuck.getName(), 25, updatedDuck.getColour(),
				updatedDuck.getHabitat());

		when(this.repo.findById(this.ID)).thenReturn(Optional.of(duck));
		// You NEED to configure a .equals() method in Duck.java for this to work
		when(this.repo.save(updatedDuck)).thenReturn(updatedDuck);
		when(this.mapper.map(updatedDuck, DuckDTO.class)).thenReturn(updatedDTO);

		assertThat(updatedDTO).isEqualTo(this.service.updateDuck(newDuck, this.ID));

		verify(this.repo, times(1)).findById(1L);
		verify(this.repo, times(1)).save(updatedDuck);
	}

}
