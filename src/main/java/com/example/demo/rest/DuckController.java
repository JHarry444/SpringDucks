package com.example.demo.rest;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistence.domain.Duck;
import com.example.demo.service.DuckService;

@RestController
public class DuckController {

	private DuckService service;

	@Autowired
	public DuckController(DuckService service) {
		super();
		this.service = service;
	}
	
	@DeleteMapping("/deleteDuck/{id}")
	public void deleteDuck(@PathVariable Long id) {
		this.service.deleteDuck(id);
	}
	
	@PutMapping("/updateDuck")
	public Duck updateDuck(@PathParam("id") Long id, @RequestBody Duck duck) {
		return this.service.updateDuck(duck, id);
	}

	@PostMapping("/createDuck")
	public Duck createDuck(@RequestBody Duck duck) {
		return this.service.createDuck(duck);
	}

	@GetMapping("/getAll")
	public List<Duck> getAll() {
		return this.service.readDucks();
	}

}
