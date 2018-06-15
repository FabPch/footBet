package com.yalafoot.bet.repository;

import com.yalafoot.bet.model.Pronostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PronosticRepository extends JpaRepository<Pronostic, Integer> {

    @Query("update pronostic p set p.prono1 = ?1, p.prono2 = ?2 where p.game_id = ?3 and p.gambler_id = ?4")
    public void update(int prono1, int prono2, int gameId, int gamblerId);
}
