package fr.arthb.motherrussia.service;

import fr.arthb.motherrussia.model.Result;

public interface ResultService {

    public Result getOne(int id);

    public Iterable<Result> findAll();

    public void save(Result result);

    public void delete(int id);
}
