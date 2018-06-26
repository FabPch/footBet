package com.yalafoot.bet.service;

import com.yalafoot.bet.model.Gambler;

public interface GamblerService {

    public Gambler getOne(int id);

    public Iterable<Gambler> findAll();

    public Iterable<Gambler> findAllOrderByGain();

    public Iterable<Gambler> findAllSecuredOrderByGain();

    public void save(Gambler gambler);

    public void delete(int id);
}
