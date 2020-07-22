package com.qa.duck.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Duck {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "duck_name", unique = true)
	@NotNull
	@Size(min = 0, max = 55)
	private String name;

	@NotNull
	private String colour;

	@NotNull
	private String habitat;

	@Min(0)
	@Max(30)
	private int age;

	@ManyToOne(targetEntity = Pond.class)
	private Pond pond;

	public Duck(String name, String colour, String habitat) {
		super();
		this.name = name;
		this.colour = colour;
		this.habitat = habitat;
	}

}
