package com.qa.duck.test.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.duck.dto.DuckDTO;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DuckControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private DuckRepo repo;

	@Autowired
	private ModelMapper modelMapper;

	private ObjectMapper mapper = new ObjectMapper();

	private long id;

	private Duck testDuck;

	private Duck testDuckWithID;

	private DuckDTO duckDTO;
	
	private DuckDTO mapToDTO(Duck duck) {
		return this.modelMapper.map(duck, DuckDTO.class);
	}

	@Before
	public void init() {
		this.repo.deleteAll();
		this.testDuck = new Duck("Barry", "blue", "pub");
		this.testDuckWithID = this.repo.save(this.testDuck);
		this.id = this.testDuckWithID.getId();
		this.duckDTO = this.mapToDTO(testDuckWithID);
	}

	@Test
	public void testCreateDuck() throws Exception {
		String result = this.mock
				.perform(request(HttpMethod.POST, "/duck/createDuck").contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(testDuck)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
		assertEquals(this.mapper.writeValueAsString(duckDTO), result);
	}

	@Test
	public void testDeleteDuck() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/duck/deleteDuck/" + this.id)).andExpect(status().isNoContent());
	}

	@Test
	public void testGetAllDucks() throws Exception {
		List<DuckDTO> duckList = new ArrayList<>();
		duckList.add(this.duckDTO);

		String content = this.mock.perform(request(HttpMethod.GET, "/duck/getAll").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(duckList), content);
	}

	@Test
	public void testUpdateDuck() throws Exception {
		Duck newDuck = new Duck("Sir Duckington esq.", "Blue", "Duckington Manor");
		Duck updatedDuck = new Duck(newDuck.getName(), newDuck.getColour(), newDuck.getHabitat());
		updatedDuck.setId(this.id);

		String result = this.mock
				.perform(request(HttpMethod.PUT, "/duck/updateDuck/?id=" + this.id).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(newDuck)))
				.andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(this.mapToDTO(updatedDuck)), result);
	}

}
