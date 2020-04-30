package com.qa.duck.rest;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.domain.Pond;
import com.qa.duck.service.PondService;

@RestController
@RequestMapping("/pond")
public class PondController {

	private PondService service;

	@Autowired
	public PondController(PondService service) {
		super();
		this.service = service;
	}

	@PostMapping("/createPond")
	public Pond createPond(@RequestBody Pond pond) {
		return this.service.createPond(pond);
	}

	@DeleteMapping("/deletePond/{id}")
	public void deletePond(@PathVariable Long id) {
		this.service.deletePond(id);
	}
	
	@GetMapping("/get/{id}")
	public Pond getPond(@PathVariable Long id) {
		return this.service.findPondByID(id);
	}

	@GetMapping("/getAll")
	public List<Pond> getAllPonds() {
		return this.service.readPonds();
	}

	@PutMapping("/updatePond")
	public Pond updatePond(@PathParam("id") Long id, @RequestBody Pond pond) {
		return this.service.updatePond(pond, id);
	}
	
	@PatchMapping("/update/{id}")
	public Pond addDuckToPond(@PathVariable Long id, @RequestBody Duck pond) {
		return this.service.addDuckToPond(id, pond);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
