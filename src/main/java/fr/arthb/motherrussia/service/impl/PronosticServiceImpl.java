package fr.arthb.motherrussia.service.impl;

import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.model.Game;
import fr.arthb.motherrussia.model.Pronostic;
import fr.arthb.motherrussia.repository.PronosticRepository;
import fr.arthb.motherrussia.service.PronosticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class PronosticServiceImpl implements PronosticService {

    @Autowired
    private PronosticRepository pronosticRepository;

    // Logger init
    private static Logger logger = LoggerFactory.getLogger(PronosticService.class);
    private static String LOG_PRFIX_UPDATE = "update() -> ";

    @Override
    public Pronostic getOne(int id) {
        return pronosticRepository.getOne(id);
    }

    @Override
    public Iterable<Pronostic> findAll() {
        return pronosticRepository.findAll();
    }

    @Override
    public void save(Pronostic pronostic) {
        pronosticRepository.save(pronostic);
    }

    @Override
    public void delete(int id) {
        pronosticRepository.deleteById(id);
    }

    @Override
    public void update(int prono1, int prono2, int gameId, int gamblerId) {
        logger.info(String.format("%sFind pronostic: pronosticRepository.getOneByGameIdAndGamblerId(%s, %s)", LOG_PRFIX_UPDATE, gameId, gamblerId));
        Pronostic currentPronostic = pronosticRepository.getOneByGameIdAndGamblerId(gameId, gamblerId);
        if(currentPronostic != null) {
            logger.info(String.format("%sPronostic found - id: %s; gamblerId: %s; pronostic: %s", LOG_PRFIX_UPDATE, currentPronostic.getId(), currentPronostic.getGambler().getId(), currentPronostic));
            logger.info(String.format("%sSet prono1: %s -> %s; prono2: %s -> %s", LOG_PRFIX_UPDATE, currentPronostic.getProno1(), prono1, currentPronostic.getProno2(), prono2));
            currentPronostic.setProno1(prono1);
            currentPronostic.setProno2(prono2);
            pronosticRepository.save(currentPronostic);
            logger.info(String.format("%sSaved pronostic", LOG_PRFIX_UPDATE));
        } else {
            logger.error(String.format("%sNo pronostic found", LOG_PRFIX_UPDATE));
        }
    }

    @Override
    public boolean checkUnicity(Pronostic pronostic, Gambler gambler){
        for (Pronostic p : gambler.getPronostics()){
            if (p.getGame().getId() == pronostic.getGame().getId()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkTime(Game game){
        Timestamp timeGame = game.getDate();
        Timestamp now = new Timestamp(new Date().getTime());
        if (now.before(timeGame)) {
            return true;
        } else {
            return false;
        }
    }
}
