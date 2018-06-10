package com.yalafoot.bet.service;

import com.yalafoot.bet.model.Crew;
import com.yalafoot.bet.model.Gambler;

import java.util.Set;

public interface CrewService {

    public Crew getOne(int id);

    public Iterable<Crew> findAll();

    public void save(Crew crew);

    public void addGamblers(int id, Set<Integer> gamblerIds);

    public void delete(int id);
}
