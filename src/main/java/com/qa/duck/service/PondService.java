package com.qa.duck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.duck.exceptions.PondNotFoundException;
import com.qa.duck.persistence.domain.Pond;
import com.qa.duck.persistence.repo.PondRepo;

@Service
public class PondService {
	
	private PondRepo repo;
	
	@Autowired
	public PondService(PondRepo repo) {
		this.repo = repo;
	}
	
	public Pond createPond(Pond duck) {
		return this.repo.save(duck);
	}
	
	public boolean deletePond(Long id) {
		if (!this.repo.existsById(id)) {
			throw new PondNotFoundException();
		}
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}
	
	public Pond findPondByID(Long id) {
		return this.repo.findById(id).orElseThrow(
				() -> new PondNotFoundException());
	}
	
	public List<Pond> readPonds() {
		return this.repo.findAll();
	}

	public Pond updatePond(Pond duck, Long id) {
		Pond toUpdate = findPondByID(id);
		toUpdate.setName(duck.getName());
		return this.repo.save(toUpdate);
	}

}
