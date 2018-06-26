package fr.arthb.motherrussia.service;

import fr.arthb.motherrussia.model.Team;

public interface TeamService {

    public Team getOne(int id);

    public Iterable<Team> findAll();

    public void save(Team team);

    public void delete(int id);
}
