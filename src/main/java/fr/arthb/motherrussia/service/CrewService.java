package fr.arthb.motherrussia.service;

import fr.arthb.motherrussia.model.Crew;

import java.util.Set;

public interface CrewService {

    public Crew getOne(int id);

    public Iterable<Crew> findAll();

    public void save(Crew crew);

    public void addGamblers(int id, Set<Integer> gamblerIds);

    public void delete(int id);
}
