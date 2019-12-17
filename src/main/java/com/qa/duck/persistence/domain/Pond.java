package com.qa.duck.persistence.domain;

import java.util.Arrays;
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

	@OneToMany
	private List<Duck> ducks;

	public Pond() {
	}

	public Pond(String name, Duck... ducks) {
		super();
		this.name = name;
		this.ducks = Arrays.asList(ducks);
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
