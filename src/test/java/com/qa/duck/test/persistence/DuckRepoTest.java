package com.qa.duck.test.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Only needed to test custom repo methods (especially when written with @query)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;

@DataJpaTest
class DuckRepoTest {

	@Autowired
	private DuckRepo repo;

	private final String TEST_COLOUR = "black";

	private final Duck TEST_DUCK = new Duck("Duck Dodger", TEST_COLOUR, "The 24th and a half century");

	private Duck testSavedDuck;

	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testSavedDuck = this.repo.save(this.TEST_DUCK);
	}

	@Test
	void testFindByName() {
		assertThat(this.repo.findByColour(this.TEST_COLOUR)).containsExactly(this.testSavedDuck);
	}

}
