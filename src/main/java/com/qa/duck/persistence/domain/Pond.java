package com.qa.duck.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	@EqualsAndHashCode.Exclude
	private Long id;

	private String name;

	@OneToMany(mappedBy = "pond")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Duck> ducks = new ArrayList<>();

	public Pond(String name) {
		this.name = name;
	}

}
