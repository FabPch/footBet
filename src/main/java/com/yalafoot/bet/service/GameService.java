package com.yalafoot.bet.service;

import com.yalafoot.bet.model.Game;

public interface GameService {

    public Game getOne(int id);

    public Iterable<Game> findAll();

    public void save(Game game);

    public void delete(int id);
}
