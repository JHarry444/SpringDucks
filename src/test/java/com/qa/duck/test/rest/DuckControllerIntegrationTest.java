package com.qa.duck.test.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.duck.dto.DuckDTO;
import com.qa.duck.persistence.domain.Duck;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:test-schema.sql",
		"classpath:test-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class DuckControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper mapper;

	private final Duck TEST_DUCK_FROM_DB = new Duck(1L, "Barry", "blue", "pub");

	private DuckDTO mapToDTO(Duck duck) {
		return this.modelMapper.map(duck, DuckDTO.class);
	}

	@Test
	void testCreateDuck() throws Exception {
		final Duck NEW_DUCK = new Duck("Donald", "White", "Toon World");
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.POST, "/duck/createDuck");
		mockRequest.contentType(MediaType.APPLICATION_JSON);
		mockRequest.content(this.mapper.writeValueAsString(NEW_DUCK));
		mockRequest.accept(MediaType.APPLICATION_JSON);

		final Duck SAVED_DUCK = new Duck(2L, NEW_DUCK.getName(), NEW_DUCK.getColour(), NEW_DUCK.getHabitat());

		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.mapper.writeValueAsString(this.mapToDTO(SAVED_DUCK)));
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);

	}

	@Test
	void testDeleteDuck() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/duck/deleteDuck/" + this.TEST_DUCK_FROM_DB.getId()))
				.andExpect(status().isNoContent());
	}

	@Test
	void testGetAllDucks() throws Exception {
		List<DuckDTO> duckList = new ArrayList<>();
		duckList.add(this.mapToDTO(TEST_DUCK_FROM_DB));

		String content = this.mock.perform(request(HttpMethod.GET, "/duck/getAll").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(duckList), content);
	}

	@Test
	void testUpdateDuck() throws Exception {
		DuckDTO newDuck = new DuckDTO(null, "Sir Duckington esq.", 25, "Blue", "Duckington Manor");
		DuckDTO updatedDuck = new DuckDTO(this.TEST_DUCK_FROM_DB.getId(), newDuck.getName(), 25, newDuck.getColour(),
				newDuck.getHabitat());

		String result = this.mock
				.perform(request(HttpMethod.PUT, "/duck/updateDuck/?id=" + this.TEST_DUCK_FROM_DB.getId())
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(newDuck)))
				.andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(updatedDuck), result);
	}

}
