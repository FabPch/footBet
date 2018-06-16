package com.yalafoot.bet.repository;

import com.yalafoot.bet.model.Pronostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PronosticRepository extends JpaRepository<Pronostic, Integer> {

    @Query("SELECT p FROM Pronostic p WHERE p.game.id = ?1 AND p.gambler.id = ?2")
    public Pronostic getOneByGameIdAndGamblerId(int gameId, int gamblerId);

}
