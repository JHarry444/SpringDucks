package com.qa.duck.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Pond {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	private Long id;

	private String name;

	@OneToMany(mappedBy = "pond")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Duck> ducks = new ArrayList<>();

	public Pond(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Pond(String name) {
		this.name = name;
	}

}
