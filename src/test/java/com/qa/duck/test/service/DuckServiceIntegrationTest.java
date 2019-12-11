package com.qa.duck.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

	@Before
	public void init() {
		this.testDuck = new Duck("Ducktor Doom", "Grey", "Latveria");
		
		this.repo.deleteAll();
		//getting around auto-generated id's
		this.testDuckWithID = this.repo.save(this.testDuck);
	}
	
	@Test
	public void testCreateDuck() {
		assertEquals(this.testDuckWithID, this.service.createDuck(testDuck));
	}

	@Test
	public void testDeleteDuck() {
		assertThat(this.service.deleteDuck(this.testDuckWithID.getId())).isFalse();
	}

	@Test
	public void testFindDuckByID() {
		assertThat(this.service.findDuckByID(this.testDuckWithID.getId())).isEqualTo(this.testDuckWithID);
	}

	@Test
	public void testReadDucks() {
		assertThat(this.service.readDucks()).isEqualTo(Arrays.asList(new Duck[] { this.testDuckWithID }));
	}

	@Test
	public void testUpdateDuck() {
		Duck newDuck = new Duck("Sir Duckington esq.", "Blue", "Duckington Manor");
		Duck updatedDuck = new Duck(newDuck.getName(), newDuck.getColour(), newDuck.getHabitat());
		updatedDuck.setId(this.testDuckWithID.getId());

		assertThat(this.service.updateDuck(newDuck, this.testDuckWithID.getId())).isEqualTo(updatedDuck);
	}

}
