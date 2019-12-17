package com.qa.duck.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.duck.persistence.domain.Pond;

@Repository
public interface PondRepo extends JpaRepository<Pond, Long> {

}
