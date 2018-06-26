package fr.arthb.motherrussia.service;

import fr.arthb.motherrussia.model.Game;

public interface GameService {

    public Game getOne(int id);

    public Iterable<Game> findAll();

    public void save(Game game);

    public void delete(int id);
}
