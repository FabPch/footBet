package com.yalafoot.bet.service;

import com.yalafoot.bet.model.Score;

public interface ScoreService {

    public Score getOne(int id);

    public Iterable<Score> findAll();

    public void save(Score score);

    public void delete(int id);
}
