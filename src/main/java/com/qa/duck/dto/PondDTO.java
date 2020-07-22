package com.qa.duck.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public final class PondDTO {

	private Long id;

	private String name;

	private List<DuckDTO> ducks = new ArrayList<>();

}
