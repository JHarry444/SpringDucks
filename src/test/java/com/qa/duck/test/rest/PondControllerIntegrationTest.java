package com.qa.duck.test.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.duck.persistence.domain.Pond;
import com.qa.duck.persistence.repo.PondRepo;

@SpringBootTest
@AutoConfigureMockMvc
class PondControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private PondRepo repo;

	private ObjectMapper mapper = new ObjectMapper();

	private long id;

	private Pond testPond;

	private Pond testPondWithID;

	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testPond = new Pond("Duckinghamshire");
		this.testPondWithID = this.repo.save(this.testPond);
		this.id = this.testPondWithID.getId();
	}

	@Test
	void testCreatePond() throws Exception {
		this.mock
				.perform(request(HttpMethod.POST, "/pond/createPond").contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(testPond)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().json(this.mapper.writeValueAsString(testPondWithID)));
	}

	@Test
	void testDeletePond() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/pond/deletePond/" + this.id)).andExpect(status().isNoContent());
	}

	@Test
	void testGetPond() throws Exception {
		this.mock.perform(request(HttpMethod.GET, "/pond/get/" + this.id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(this.mapper.writeValueAsString(this.testPond)));
	}

	@Test
	void testGetAllPonds() throws Exception {
		List<Pond> pondList = new ArrayList<>();
		pondList.add(this.testPondWithID);

		this.mock.perform(request(HttpMethod.GET, "/pond/getAll").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(this.mapper.writeValueAsString(pondList)));
	}

	@Test
	void testUpdatePond() throws Exception {
		Pond newPond = new Pond("Amy");
		Pond updatedPond = new Pond(newPond.getName());
		updatedPond.setId(this.id);

		this.mock
				.perform(request(HttpMethod.PUT, "/pond/updatePond/?id=" + this.id).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(newPond)))
				.andExpect(status().isAccepted())
				.andExpect(content().json(this.mapper.writeValueAsString(updatedPond)));
	}

}
