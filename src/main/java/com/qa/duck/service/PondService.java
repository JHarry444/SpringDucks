package com.qa.duck.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.duck.dto.PondDTO;
import com.qa.duck.exceptions.PondNotFoundException;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.domain.Pond;
import com.qa.duck.persistence.repo.DuckRepo;
import com.qa.duck.persistence.repo.PondRepo;

@Service
public class PondService {

	private PondRepo repo;

	private DuckRepo duckRepo;

	private Mapper<Pond, PondDTO> mapper;

	@Autowired
	public PondService(PondRepo repo, DuckRepo duckRepo, ModelMapper mapper) {
		this.repo = repo;
		this.duckRepo = duckRepo;
		this.mapper = (Pond pond) -> mapper.map(pond, PondDTO.class);
	}

	public PondDTO createPond(Pond pond) {
		return this.mapper.mapToDTO(this.repo.save(pond));
	}

	public boolean deletePond(Long id) {
		if (!this.repo.existsById(id)) {
			throw new PondNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public PondDTO findPondByID(Long id) {
		return this.mapper.mapToDTO(this.repo.findById(id).orElseThrow(() -> new PondNotFoundException()));
	}

	public List<PondDTO> readPonds() {
		return this.repo.findAll().stream().map(this.mapper::mapToDTO).collect(Collectors.toList());
	}

	public PondDTO updatePond(Pond duck, Long id) {
		Pond toUpdate = this.repo.findById(id).orElseThrow(() -> new PondNotFoundException());
		toUpdate.setName(duck.getName());
		return this.mapper.mapToDTO(this.repo.save(toUpdate));
	}

	public PondDTO addDuckToPond(Long id, Duck duck) {
		Pond toUpdate = this.repo.findById(id).orElseThrow(() -> new PondNotFoundException());
		Duck newDuck = this.duckRepo.save(duck);
		toUpdate.getDucks().add(newDuck);
		return this.mapper.mapToDTO(this.repo.saveAndFlush(toUpdate));
	}

}
