package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.model.Crew;
import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.repository.CrewRepository;
import com.yalafoot.bet.service.CrewService;
import com.yalafoot.bet.service.GamblerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CrewServiceImpl implements CrewService {

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private GamblerService gamblerService;

    @Override
    public Crew getOne(int id) {
        return crewRepository.getOne(id);
    }

    @Override
    public Iterable<Crew> findAll() {
        return crewRepository.findAll();
    }

    @Override
    public void save(Crew crew) {
        crewRepository.save(crew);
    }

    @Override
    public void delete(int id) {
        crewRepository.deleteById(id);
    }

    @Override
    public void addGamblers(int id, Set<Integer> gamblerIds) {
        Crew crew = getOne(id);
        Set<Gambler> gamblers = new HashSet<Gambler>();
        for (Integer i : gamblerIds){
            gamblers.add(gamblerService.getOne(i));
        }
        crew.addGamblers(gamblers);
        crewRepository.save(crew);
    }
}
