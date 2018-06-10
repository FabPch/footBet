package com.yalafoot.bet.service;

import com.yalafoot.bet.model.Team;

public interface TeamService {

    public Team getOne(int id);

    public Iterable<Team> findAll();

    public void save(Team team);

    public void delete(int id);
}
