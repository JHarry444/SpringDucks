package com.qa.duck.test.persistence.domain;

import org.junit.jupiter.api.Test;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.dto.PondDTO;

import nl.jqno.equalsverifier.EqualsVerifier;

class CoverageTest {

//	@Test
//	void testDuck() {
//		EqualsVerifier.simple().forClass(Duck.class).withPrefabValues(Pond.class, new Pond(), new Pond("poole"))
//				.verify();
//	}

	@Test
	void testDuckDTO() {
		EqualsVerifier.simple().forClass(DuckDTO.class).verify();
	}

//	@Test
//	void testPond() {
//		EqualsVerifier.simple().forClass(Pond.class)
//				.withPrefabValues(Duck.class, new Duck(), new Duck("Daffy", "Black", "WB Studios")).verify();
//	}

	@Test
	void testPondDTO() {
		EqualsVerifier.simple().forClass(PondDTO.class).verify();
	}

}
