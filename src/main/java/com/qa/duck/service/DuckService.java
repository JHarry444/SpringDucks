package com.qa.duck.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.duck.dto.DuckDTO;
import com.qa.duck.exceptions.DuckNotFoundException;
import com.qa.duck.persistence.domain.Duck;
import com.qa.duck.persistence.repo.DuckRepo;
import com.qa.duck.utils.MyBeanUtils;

@Service
public class DuckService {

	private DuckRepo repo;

	private Mapper<Duck, DuckDTO> mapper;

	@Autowired
	public DuckService(DuckRepo repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = (Duck duck) -> mapper.map(duck, DuckDTO.class);
	}

	public DuckDTO createDuck(Duck duck) {
		return this.mapper.mapToDTO(this.repo.save(duck));
	}

	public boolean deleteDuck(Long id) {
		if (!this.repo.existsById(id)) {
			throw new DuckNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public DuckDTO findDuckByID(Long id) {
		return this.mapper.mapToDTO(this.repo.findById(id).orElseThrow(DuckNotFoundException::new));
	}

	public List<DuckDTO> readDucks() {
		return this.repo.findAll().stream().map(this.mapper::mapToDTO).collect(Collectors.toList());
	}

	public DuckDTO updateDuck(Duck duck, Long id) {
		Duck toUpdate = this.repo.findById(id).orElseThrow(DuckNotFoundException::new);
		MyBeanUtils.mergeNotNull(duck, toUpdate);
		return this.mapper.mapToDTO(this.repo.save(toUpdate));
	}

}
