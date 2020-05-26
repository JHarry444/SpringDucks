package com.qa.duck.service;

public interface Mapper<Source, Target> {

	Target mapToDTO(Source source);
}
