package com.qa.duck.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.duck.dto.PondDTO;
import com.qa.duck.exceptions.PondNotFoundException;
import com.qa.duck.persistence.domain.Pond;
import com.qa.duck.persistence.repo.PondRepo;

@Service
public class PondService {

	private PondRepo repo;

	private ModelMapper mapper;

	@Autowired
	public PondService(PondRepo repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	private PondDTO mapToDTO(Pond pond) {
		return this.mapper.map(pond, PondDTO.class);
	}

	private Pond mapFromDTO(PondDTO pond) {
		return this.mapper.map(pond, Pond.class);
	}

	public PondDTO createPond(PondDTO pond) {
		Pond toSave = this.mapFromDTO(pond);
		Pond saved = this.repo.save(toSave);
		return this.mapToDTO(saved);
	}

	public boolean deletePond(Long id) {
		if (!this.repo.existsById(id)) {
			throw new PondNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public PondDTO findPondByID(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(PondNotFoundException::new));
	}

	public List<PondDTO> readPonds() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public PondDTO updatePond(PondDTO pond, Long id) {
		Pond toUpdate = this.repo.findById(id).orElseThrow(PondNotFoundException::new);
		toUpdate.setName(pond.getName());
		return this.mapToDTO(this.repo.save(toUpdate));
	}

}
