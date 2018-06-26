package fr.arthb.motherrussia.service;

import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.model.Game;
import fr.arthb.motherrussia.model.Pronostic;

public interface PronosticService {

    public Pronostic getOne(int id);

    public Iterable<Pronostic> findAll();

    public void save(Pronostic pronostic);

    public void delete(int id);

    public void update(int prono1, int prono2, int gameId, int gamblerId);

    public boolean checkUnicity(Pronostic pronostic, Gambler gambler);

    public boolean checkTime(Game game);
}
