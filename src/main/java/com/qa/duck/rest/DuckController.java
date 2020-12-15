package com.qa.duck.rest;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.service.DuckService;

@RestController
@RequestMapping("/duck")
@CrossOrigin
public class DuckController {

	private DuckService service;

	@Autowired
	public DuckController(DuckService service) {
		super();
		this.service = service;
	}

	@PostMapping("/createDuck")
	public ResponseEntity<DuckDTO> createDuck(@RequestBody DuckDTO duck) {
		return new ResponseEntity<>(this.service.createDuck(duck), HttpStatus.CREATED);
	}

	@DeleteMapping("/deleteDuck/{id}")
	public ResponseEntity<DuckDTO> deleteDuck(@PathVariable Long id) {
		return this.service.deleteDuck(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<DuckDTO> getDuck(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.findDuckByID(id));
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<DuckDTO>> getAllDucks() {
		return ResponseEntity.ok(this.service.readDucks());
	}
	
	@GetMapping("/getAll/{field}/{direction}")
	public ResponseEntity<List<DuckDTO>> sortAndFilterDucks(@PathVariable String field, @PathVariable Direction direction, @RequestBody Duck duck) {
		return ResponseEntity.ok(this.service.sortAndFilterDucks(direction, field, duck));
	}

	@PutMapping("/updateDuck")
	public ResponseEntity<DuckDTO> updateDuck(@PathParam("id") Long id, @RequestBody DuckDTO duck) {
		return new ResponseEntity<>(this.service.updateDuck(duck, id), HttpStatus.ACCEPTED);
	}

}
