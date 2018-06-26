package fr.arthb.motherrussia.service;

import fr.arthb.motherrussia.model.Score;

public interface ScoreService {

    public Score getOne(int id);

    public Iterable<Score> findAll();

    public void save(Score score);

    public void delete(int id);
}
