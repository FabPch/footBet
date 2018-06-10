package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.model.Game;
import com.yalafoot.bet.repository.GameRepository;
import com.yalafoot.bet.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game getOne(int id) {
        return gameRepository.getOne(id);
    }

    @Override
    public Iterable<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void delete(int id) {
        gameRepository.deleteById(id);
    }
}
