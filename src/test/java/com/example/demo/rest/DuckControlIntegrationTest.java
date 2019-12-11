package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.persistence.domain.Duck;
import com.example.demo.service.DuckService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

@RunWith(SpringRunner.class)
@WebMvcTest(DuckController.class)
@AutoConfigureMockMvc
public class DuckControlIntegrationTest {

	@Autowired
	private MockMvc mock;

	@MockBean
	private DuckService service;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testCreateDuck() throws JsonProcessingException, Exception {
		Duck d1 = new Duck("Barry", "blue", "pub");
		Duck d2 = new Duck("Barry", "blue", "pub");
		d2.setId(1L);
		
		Mockito.when(this.service.createDuck(d1)).thenReturn(d2);
		
		this.mock.perform(
					request(HttpMethod.POST, "/createDuck")
							.contentType(MediaType.APPLICATION_JSON)
							.content(this.mapper.writeValueAsString(d1))
							.accept(MediaType.APPLICATION_JSON)
						).andExpect(status().isOk());
	}

}
