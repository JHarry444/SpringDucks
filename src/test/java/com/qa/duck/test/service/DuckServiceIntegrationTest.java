package com.qa.duck.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;
import com.qa.duck.service.DuckService;

@SpringBootTest
class DuckServiceIntegrationTest {

	@Autowired
	private DuckService service;

	@Autowired
	private DuckRepo repo;

	private Duck testDuck;

	private Duck testDuckWithID;

	@Autowired
	private ModelMapper mapper;

	private DuckDTO mapToDTO(Duck duck) {
		return this.mapper.map(duck, DuckDTO.class);
	}

	@BeforeEach
	void init() {
		this.testDuck = new Duck("Ducktor Doom", "Grey", "Latveria");

		this.repo.deleteAll();
		// getting around auto-generated id's
		this.testDuckWithID = this.repo.save(this.testDuck);
	}

	@Test
	void testCreateDuck() {
		assertThat(this.mapToDTO(this.testDuckWithID)).isEqualTo(this.service.createDuck(mapToDTO(testDuck)));
	}

	@Test
	void testDeleteDuck() {
		assertThat(this.service.deleteDuck(this.testDuckWithID.getId())).isTrue();
	}

	@Test
	void testFindDuckByID() {
		assertThat(this.service.findDuckByID(this.testDuckWithID.getId()))
				.isEqualTo(this.mapToDTO(this.testDuckWithID));
	}

	@Test
	void testReadDucks() {
		assertThat(this.service.readDucks())
				.isEqualTo(Stream.of(this.mapToDTO(testDuckWithID)).collect(Collectors.toList()));
	}

	@Test
	void testUpdateDuck() {
		DuckDTO newDuck = new DuckDTO(null, "Sir Duckington esq.", 25, "Blue", "Duckington Manor");
		DuckDTO updatedDuck = new DuckDTO(this.testDuckWithID.getId(), newDuck.getName(), 25, newDuck.getColour(),
				newDuck.getHabitat());

		assertThat(this.service.updateDuck(newDuck, this.testDuckWithID.getId())).isEqualTo(updatedDuck);
	}

}
