package com.qa.duck.test.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

//Only needed to test custom repo methods (especially when written with @query)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;

@DataJpaTest
@Sql(scripts = { "classpath:test-schema.sql",
		"classpath:test-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class DuckRepoTest {

	@Autowired
	private DuckRepo repo;

	@Test
	void testFindByName() {
		final String colour = "blue";

		final Duck testDuck = new Duck(1L, "Barry", colour, "pub", 0);
		assertThat(this.repo.findByColour(colour)).containsExactly(testDuck);
	}

}
