package com.yalafoot.bet.repository;

import com.yalafoot.bet.model.Pronostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PronosticRepository extends JpaRepository<Pronostic, Integer> {
}
