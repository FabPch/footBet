package com.yalafoot.bet.repository;

import com.yalafoot.bet.model.Gambler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GamblerRepository extends JpaRepository<Gambler, Integer> {

    @Query("SELECT g FROM Gambler u WHERE u.login = ?1")
    public Gambler findByUsername(String userName);
}
