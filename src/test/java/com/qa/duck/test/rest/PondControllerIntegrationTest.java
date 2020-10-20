package com.qa.duck.test.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.duck.dto.PondDTO;
import com.qa.duck.persistence.domain.Pond;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:test-schema.sql",
		"classpath:test-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class PondControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper mapper;

	private PondDTO mapToDTO(Pond pond) {
		return this.modelMapper.map(pond, PondDTO.class);
	}

	private long id = 1L;

	final private Pond TEST_POND = new Pond(this.id, "Duckinghamshire");

	@BeforeEach
	void init() {
	}

	@Test
	void testCreatePond() throws Exception {
		Pond newPond = new Pond("Amy");
		Pond savedPond = new Pond(newPond.getName());
		savedPond.setId(this.id + 1);
		PondDTO savedPondDTO = this.mapToDTO(savedPond);
		this.mock
				.perform(request(HttpMethod.POST, "/pond/createPond").contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(newPond)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().json(this.mapper.writeValueAsString(savedPondDTO)));
	}

	@Test
	void testDeletePond() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/pond/deletePond/" + this.id)).andExpect(status().isNoContent());
	}

	@Test
	void testGetPond() throws Exception {
		PondDTO expectedResult = this.mapToDTO(TEST_POND);
		this.mock.perform(request(HttpMethod.GET, "/pond/get/" + this.id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(this.mapper.writeValueAsString(expectedResult)));
	}

	@Test
	void testGetAllPonds() throws Exception {
		List<PondDTO> pondList = new ArrayList<>();
		pondList.add(this.mapToDTO(TEST_POND));

		this.mock.perform(request(HttpMethod.GET, "/pond/getAll").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(this.mapper.writeValueAsString(pondList)));
	}

	@Test
	void testUpdatePond() throws Exception {
		Pond newPond = new Pond("Amy");
		Pond updatedPond = new Pond(this.id, newPond.getName());
		PondDTO updatedPondDTO = this.mapToDTO(updatedPond);
		this.mock
				.perform(request(HttpMethod.PUT, "/pond/updatePond/?id=" + this.id).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(newPond)))
				.andExpect(status().isAccepted())
				.andExpect(content().json(this.mapper.writeValueAsString(updatedPondDTO)));
	}

}
