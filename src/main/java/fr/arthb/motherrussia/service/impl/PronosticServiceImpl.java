package fr.arthb.motherrussia.service.impl;

import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.model.Game;
import fr.arthb.motherrussia.model.Pronostic;
import fr.arthb.motherrussia.repository.PronosticRepository;
import fr.arthb.motherrussia.service.PronosticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class PronosticServiceImpl implements PronosticService {

    @Autowired
    private PronosticRepository pronosticRepository;

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
        Pronostic currentPronostic = pronosticRepository.getOneByGameIdAndGamblerId(gameId, gamblerId);
        currentPronostic.setProno1(prono1);
        currentPronostic.setProno2(prono2);
        pronosticRepository.save(currentPronostic);
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
