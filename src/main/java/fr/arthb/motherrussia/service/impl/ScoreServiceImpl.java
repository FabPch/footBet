package fr.arthb.motherrussia.service.impl;

import fr.arthb.motherrussia.model.Score;
import fr.arthb.motherrussia.repository.ScoreRepository;
import fr.arthb.motherrussia.service.ScoreService;
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
