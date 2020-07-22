package com.qa.duck.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;
import com.qa.duck.service.DuckService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DuckServiceIntegrationTest {

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

	@Before
	public void init() {
		this.testDuck = new Duck("Ducktor Doom", "Grey", "Latveria");

		this.repo.deleteAll();
		// getting around auto-generated id's
		this.testDuckWithID = this.repo.save(this.testDuck);
	}

	@Test
	public void testCreateDuck() {
		assertEquals(this.mapToDTO(this.testDuckWithID), this.service.createDuck(mapToDTO(testDuck)));
	}

	@Test
	public void testDeleteDuck() {
		assertThat(this.service.deleteDuck(this.testDuckWithID.getId())).isTrue();
	}

	@Test
	public void testFindDuckByID() {
		assertThat(this.service.findDuckByID(this.testDuckWithID.getId()))
				.isEqualTo(this.mapToDTO(this.testDuckWithID));
	}

	@Test
	public void testReadDucks() {
		assertThat(this.service.readDucks())
				.isEqualTo(Stream.of(this.mapToDTO(testDuckWithID)).collect(Collectors.toList()));
	}

	@Test
	public void testUpdateDuck() {
		DuckDTO newDuck = new DuckDTO(null, "Sir Duckington esq.", "Blue", "Duckington Manor");
		DuckDTO updatedDuck = new DuckDTO(this.testDuckWithID.getId(), newDuck.getName(), newDuck.getColour(),
				newDuck.getHabitat());

		assertThat(this.service.updateDuck(newDuck, this.testDuckWithID.getId())).isEqualTo(updatedDuck);
	}

}
