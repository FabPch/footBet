package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.model.Pronostic;
import com.yalafoot.bet.repository.PronosticRepository;
import com.yalafoot.bet.service.PronosticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PronosticServiceImpl implements PronosticService {

    @Autowired
    private PronosticRepository pronosticRepository;

    @Override
    public Pronostic getOne(int id) {
        return pronosticRepository.getOne(id);
    }

    @Override
    public Iterable<Pronostic> findAll() {
        return pronosticRepository.findAll();
    }

    @Override
    public void save(Pronostic pronostic) {
        pronosticRepository.save(pronostic);
    }

    @Override
    public void delete(int id) {
        pronosticRepository.deleteById(id);
    }
}
