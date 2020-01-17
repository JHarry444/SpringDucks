package com.qa.duck.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;
import com.qa.duck.service.DuckService;

@RunWith(SpringRunner.class)
public class DuckServiceUnitTest {

	@InjectMocks
	private DuckService service;

	@Mock
	private DuckRepo repo;

	private List<Duck> duckList;

	private Duck testDuck;

	private Duck testDuckWithID;

	final long id = 1L;
	


	@Test
	public void createDuckTest() {
		when(this.repo.save(testDuck)).thenReturn(testDuckWithID);

		assertEquals(this.testDuckWithID, this.service.createDuck(testDuck));

		verify(this.repo, times(1)).save(this.testDuck);
	}

	@Test
	public void deleteDuckTest() {
		when(this.repo.existsById(id)).thenReturn(true, false);

		this.service.deleteDuck(id);

		verify(this.repo, times(1)).deleteById(id);
		verify(this.repo, times(2)).existsById(id);
	}

	@Test
	public void findDuckByIDTest() {
		when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testDuckWithID));

		assertEquals(this.testDuckWithID, this.service.findDuckByID(this.id));

		verify(this.repo, times(1)).findById(this.id);
	}

	@Test
	public void readDucksTest() {

		when(repo.findAll()).thenReturn(this.duckList);

		assertFalse("Controller has found no ducks", this.service.readDucks().isEmpty());

		verify(repo, times(1)).findAll();
	}

	@Test
	public void updateDucksTest() {
		// given
		Duck newDuck = new Duck("Sir Duckington esq.", "Blue", "Duckington Manor");
		
		when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testDuckWithID));

		Duck updatedDuck = new Duck(newDuck.getName(), 
										newDuck.getColour(), newDuck.getHabitat());
		updatedDuck.setId(this.id);

		// You NEED to configure a .equals() method in Duck.java for this to work
		when(this.repo.save(updatedDuck)).thenReturn(updatedDuck);

		assertEquals(updatedDuck, this.service.updateDuck(newDuck, this.id));

		verify(this.repo, times(1)).findById(1L);
		verify(this.repo, times(1)).save(updatedDuck);
	}
	
	@Before
	public void init() {
		this.duckList = new ArrayList<>();
		this.duckList.add(testDuck);
		this.testDuck = new Duck("Ducktor Doom", "Grey", "Latveria");
		this.testDuckWithID = new Duck(testDuck.getName(), testDuck.getColour(), testDuck.getHabitat());
		this.testDuckWithID.setId(id);
	}

}
