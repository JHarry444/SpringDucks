package com.qa.duck.persistence;

import static org.assertj.core.api.Assertions.assertThat;

//Only needed to test custom repo methods (especially when written with @query)

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DuckRepoTest {

	@Autowired
	private DuckRepo repo;

	private final String TEST_COLOUR = "black";

	private final Duck TEST_DUCK = new Duck("Duck Dodger", TEST_COLOUR, "The 24th and a half century");

	private Duck testSavedDuck;

	@Before
	public void init() {
		this.repo.deleteAll();
		this.testSavedDuck = this.repo.save(this.TEST_DUCK);
	}

	@Test
	public void testFindByName() {
		assertThat(this.repo.findByColour(this.TEST_COLOUR)).containsExactly(this.testSavedDuck);
	}

}
