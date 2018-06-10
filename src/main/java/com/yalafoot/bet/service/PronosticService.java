package com.yalafoot.bet.service;

import com.yalafoot.bet.model.Pronostic;

public interface PronosticService {

    public Pronostic getOne(int id);

    public Iterable<Pronostic> findAll();

    public void save(Pronostic pronostic);

    public void delete(int id);
}
