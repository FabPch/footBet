package fr.arthb.motherrussia.service.impl;

import fr.arthb.motherrussia.model.Team;
import fr.arthb.motherrussia.repository.TeamRepository;
import fr.arthb.motherrussia.service.TeamService;
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
