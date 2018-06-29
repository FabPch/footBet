package fr.arthb.motherrussia.service;

import fr.arthb.motherrussia.model.Game;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public interface GameService {

    public Game getOne(int id);

    public ArrayList<Game> findAll();

    public void save(Game game);

    public void delete(int id);

    public JSONObject getLiveGames();
}
