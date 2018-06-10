package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.model.Team;
import com.yalafoot.bet.repository.TeamRepository;
import com.yalafoot.bet.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team getOne(int id) {
        return teamRepository.getOne(id);
    }

    @Override
    public Iterable<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void delete(int id) {
        teamRepository.deleteById(id);
    }
}
