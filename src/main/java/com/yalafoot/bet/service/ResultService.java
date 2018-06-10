package com.yalafoot.bet.service;

import com.yalafoot.bet.model.Result;

public interface ResultService {

    public Result getOne(int id);

    public Iterable<Result> findAll();

    public void save(Result result);

    public void delete(int id);
}
