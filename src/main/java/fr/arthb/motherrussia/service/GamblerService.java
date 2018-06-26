package fr.arthb.motherrussia.service;

import fr.arthb.motherrussia.model.Gambler;

public interface GamblerService {

    public Gambler getOne(int id);

    public Iterable<Gambler> findAll();

    public Iterable<Gambler> findAllOrderByGain();

    public Iterable<Gambler> findAllSecuredOrderByGain();

    public void save(Gambler gambler);

    public void delete(int id);
}
