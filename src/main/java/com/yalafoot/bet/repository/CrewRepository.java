package com.yalafoot.bet.repository;

import com.yalafoot.bet.model.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Integer> {
}
