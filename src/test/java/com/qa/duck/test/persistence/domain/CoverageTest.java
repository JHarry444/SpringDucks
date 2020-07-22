package com.qa.duck.test.persistence.domain;

import org.junit.jupiter.api.Test;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.dto.PondDTO;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.domain.Pond;

import nl.jqno.equalsverifier.EqualsVerifier;

class CoverageTest {

	@Test
	void testDuck() {
		EqualsVerifier.forClass(Duck.class).withPrefabValues(Pond.class, new Pond(), new Pond("poole")).verify();
	}

	@Test
	void testDuckDTO() {
		EqualsVerifier.simple().forClass(DuckDTO.class).verify();
	}

	@Test
	void testPond() {
		EqualsVerifier.forClass(Pond.class)
				.withPrefabValues(Duck.class, new Duck(), new Duck("Daffy", "Black", "WB Studios")).verify();
	}

	@Test
	void testPondDTO() {
		EqualsVerifier.simple().forClass(PondDTO.class).verify();
	}

}
