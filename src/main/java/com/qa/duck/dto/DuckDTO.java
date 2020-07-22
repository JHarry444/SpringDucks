package com.qa.duck.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DuckDTO {

	private Long id;

	private String name;

	private String colour;

	private String habitat;

	public DuckDTO(Long id, String name, String colour, String habitat) {
		super();
		this.id = id;
		this.name = name;
		this.colour = colour;
		this.habitat = habitat;
	}

}
