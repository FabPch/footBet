package fr.arthb.motherrussia.repository;

import fr.arthb.motherrussia.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    @Query("SELECT g.id FROM Game g WHERE g.date<?1 AND g.processed = 0")
    public int getGameIdByProcessedAndTimePlus90Min(Date dateActuellePlusUneHeure);
}
