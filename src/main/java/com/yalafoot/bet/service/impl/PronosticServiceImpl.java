package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.constants.AppConstants;
import com.yalafoot.bet.exception.CustomException;
import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.model.Game;
import com.yalafoot.bet.model.Pronostic;
import com.yalafoot.bet.repository.PronosticRepository;
import com.yalafoot.bet.service.PronosticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public void update(Pronostic pronostic) {
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
