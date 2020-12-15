package com.qa.duck.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public final class DuckDTO {

	private Long id;

	private String name;
	
	private int age;

	private String colour;

	private String habitat;

}
