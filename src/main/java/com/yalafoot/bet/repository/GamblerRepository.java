package com.yalafoot.bet.repository;

import com.yalafoot.bet.model.Gambler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GamblerRepository extends JpaRepository<Gambler, Integer> {

    @Query("SELECT g  FROM Gambler g WHERE g.login = ?1")
    public Gambler findByLogin(String login);

    @Query("SELECT SUM(p.gain) AS gain FROM Pronostic p WHERE p.gambler.id = ?1")
    public int getGainSum(int gambler_id);

    @Query("SELECT id, md5(login), name, gain  FROM Gambler g ORDER BY g.gain DESC")
    public Iterable<Gambler> findAllOrderByGain();
}
