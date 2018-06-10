package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.model.Score;
import com.yalafoot.bet.repository.ScoreRepository;
import com.yalafoot.bet.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public Score getOne(int id) {
        return scoreRepository.getOne(id);
    }

    @Override
    public Iterable<Score> findAll() {
        return scoreRepository.findAll();
    }

    @Override
    public void save(Score score) {
        scoreRepository.save(score);
    }

    @Override
    public void delete(int id) {
        scoreRepository.deleteById(id);
    }
}
