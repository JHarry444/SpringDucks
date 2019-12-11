package com.qa.duck.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.rest.DuckController;
import com.qa.duck.service.DuckService;

@RunWith(SpringRunner.class)
@WebMvcTest(DuckController.class)
@AutoConfigureMockMvc
public class DuckControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@MockBean
	private DuckService service;

	private ObjectMapper mapper = new ObjectMapper();

	private final long id = 1L;
	
	private Duck testDuck;
	
	private Duck testDuckWithID;
	
	@Before
	public void init() {
		this.testDuck= new Duck("Barry", "blue", "pub");
		this.testDuckWithID = new Duck(testDuck.getName(), testDuck.getColour(), testDuck.getHabitat());
		this.testDuckWithID.setId(this.id);
	}

	@Test
	public void testCreateDuck() throws Exception {
		Mockito.when(this.service.createDuck(testDuck)).thenReturn(testDuckWithID);

		String result = 
				this.mock.perform(
					request(HttpMethod.POST, "/createDuck")
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(testDuck))
						.accept(MediaType.APPLICATION_JSON)
					).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(this.mapper.writeValueAsString(testDuckWithID), result);
		
		verify(this.service, times(1)).createDuck(testDuck);
	}

	@Test
	public void testDeleteDuck() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/deleteDuck/" + this.id)).andExpect(status().isOk());
	
		verify(this.service, times(1)).deleteDuck(this.id);
	}

	@Test
	public void testGetAllDucks() throws Exception {
		List<Duck> duckList = new ArrayList<>();
		duckList.add(this.testDuckWithID);
		
		when(this.service.readDucks()).thenReturn(duckList);

		String content = this.mock.perform(request(HttpMethod.GET, "/getAll")
													.accept(MediaType.APPLICATION_JSON))
													.andExpect(status().isOk()
												).andReturn().getResponse().getContentAsString();
		
		assertEquals(this.mapper.writeValueAsString(duckList), content);
		
		verify(this.service, times(1)).readDucks();
	}

	@Test
	public void testUpdateDuck() throws Exception {
		Duck newDuck = new Duck("Sir Duckington esq.", "Blue", "Duckington Manor");
		Duck updatedDuck = new Duck(newDuck.getName(), newDuck.getColour(), newDuck.getHabitat());
		updatedDuck.setId(this.id);
		
		when(this.service.updateDuck(newDuck, this.id)).thenReturn(updatedDuck);
		
		String result = this.mock
				.perform(request(HttpMethod.PUT, "/updateDuck/?id="+ this.id)
							.accept(MediaType.APPLICATION_JSON)
							.contentType(MediaType.APPLICATION_JSON)
							.content(this.mapper.writeValueAsString(newDuck)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		assertEquals(this.mapper.writeValueAsString(updatedDuck), result);
		
		verify(this.service, times(1)).updateDuck(newDuck, this.id);
	}

}
