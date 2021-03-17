package com.qa.duck.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.exceptions.DuckNotFoundException;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;
import com.qa.duck.utils.MyBeanUtils;

@Service
public class DuckService {

	private DuckRepo repo;

	private ModelMapper mapper;

	@Autowired
	public DuckService(DuckRepo repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	private DuckDTO mapToDTO(Duck duck) {
		return this.mapper.map(duck, DuckDTO.class);
	}

	private Duck mapFromDTO(DuckDTO duck) {
		return this.mapper.map(duck, Duck.class);
	}

	public DuckDTO createDuck(DuckDTO duckDTO) {
		Duck toSave = this.mapFromDTO(duckDTO);
		Duck saved = this.repo.save(toSave);
		return this.mapToDTO(saved);
	}

	public boolean deleteDuck(Long id) {
		if (!this.repo.existsById(id)) {
			throw new DuckNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public DuckDTO findDuckByID(Long id) {
		final Duck found = this.repo.findById(id)
				.orElseThrow(() -> {
					return new ResponseStatusException(HttpStatus.NOT_FOUND, "This duck does not exist");
				});
		return this.mapToDTO(found);
	}

	public List<DuckDTO> readDucks() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	public List<DuckDTO> sortAndFilterDucks(Direction direction, String field, Duck duck) {
		return this.repo.findAll(Example.of(duck), Sort.by(direction, field)).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public DuckDTO updateDuck(DuckDTO duck, Long id) {
		Duck toUpdate = this.repo.findById(id).orElseThrow(DuckNotFoundException::new);
		MyBeanUtils.mergeNotNull(duck, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}

}
