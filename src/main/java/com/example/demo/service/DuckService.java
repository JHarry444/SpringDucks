package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.DuckNotFoundException;
import com.example.demo.persistence.domain.Duck;
import com.example.demo.persistence.repo.DuckRepo;

@Service
public class DuckService {
	
	
	private DuckRepo repo;
	
	@Autowired
	public DuckService(DuckRepo repo) {
		this.repo = repo;
	}
	
	public Duck createDuck(Duck duck) {
		return this.repo.save(duck);
	}
	
	public boolean deleteDuck(Long id) {
		if (!this.repo.existsById(id)) {
			throw new DuckNotFoundException();
		}
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}
	
	public Duck findDuckByID(Long id) {
		return this.repo.findById(id).orElseThrow(
				() -> new DuckNotFoundException());
	}
	
	public List<Duck> readDucks() {
		return this.repo.findAll();
	}

	public Duck updateDuck(Duck duck, Long id) {
		Duck toUpdate = findDuckByID(id);
		toUpdate.setName(duck.getName());
		toUpdate.setColour(duck.getColour());
		toUpdate.setHabitat(duck.getHabitat());
		return this.repo.save(toUpdate);
	}

}
