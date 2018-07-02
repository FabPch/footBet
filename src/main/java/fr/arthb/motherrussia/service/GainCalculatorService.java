package fr.arthb.motherrussia.service;

import fr.arthb.motherrussia.model.Game;

import javax.servlet.http.HttpServletRequest;

public interface GainCalculatorService {

    public void process(Game game);

    public void process(int gameId);

    public int getGameIdByProcessedAndTimePlus90Min();

}
