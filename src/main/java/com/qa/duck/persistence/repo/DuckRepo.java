package com.qa.duck.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.duck.persistence.domain.Duck;

@Repository
public interface DuckRepo extends JpaRepository<Duck, Long> {

	List<Duck> findByColour(String colour);
		
}
