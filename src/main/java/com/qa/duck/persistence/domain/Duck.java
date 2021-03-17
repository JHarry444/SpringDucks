package com.qa.duck.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Duck {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
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
	private Integer age;

	@ManyToOne(targetEntity = Pond.class)
	private Pond pond = null;

	public Duck(Long id, @NotNull @Size(min = 0, max = 55) String name, @NotNull String colour,
			@NotNull String habitat) {
		super();
		this.id = id;
		this.name = name;
		this.colour = colour;
		this.habitat = habitat;
	}

	public Duck(@NotNull @Size(min = 0, max = 55) String name, @NotNull String colour, @NotNull String habitat) {
		super();
		this.name = name;
		this.colour = colour;
		this.habitat = habitat;
	}

	public Duck(Long id, @NotNull @Size(min = 0, max = 55) String name, @NotNull String colour, @NotNull String habitat,
			@Min(0) @Max(30) Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.colour = colour;
		this.habitat = habitat;
		this.age = age;
	}

}
