package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.repository.GamblerRepository;
import com.yalafoot.bet.service.GamblerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamblerServiceImpl implements GamblerService {

    @Autowired
    private GamblerRepository gamblerRepository;

    @Override
    public Gambler getOne(int id) {
        return gamblerRepository.getOne(id);
    }

    @Override
    public Iterable<Gambler> findAll() {
        return gamblerRepository.findAll();
    }

    @Override
    public void save(Gambler gambler) {
        gamblerRepository.save(gambler);
    }

    @Override
    public void delete(int id) {
        gamblerRepository.deleteById(id);
    }
}
