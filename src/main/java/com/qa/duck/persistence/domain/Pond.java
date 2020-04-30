package com.qa.duck.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Pond {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToMany(mappedBy = "pond")
	private List<Duck> ducks = new ArrayList<>();
	
	public Pond(String name) {
		this.name = name;
	}

	public Pond() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Duck> getDucks() {
		return ducks;
	}

	public void setDucks(List<Duck> ducks) {
		this.ducks = ducks;
	}

}
