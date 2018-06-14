package com.yalafoot.bet.service;

import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.model.Game;
import com.yalafoot.bet.model.Pronostic;

import javax.servlet.http.HttpServletRequest;

public interface PronosticService {

    public Pronostic getOne(int id);

    public Iterable<Pronostic> findAll();

    public void save(Pronostic pronostic);

    public void delete(int id);

    public void update(Pronostic pronostic);

    public boolean checkUnicity(Pronostic pronostic, Gambler gambler);

    public boolean checkTime(Game game);
}
