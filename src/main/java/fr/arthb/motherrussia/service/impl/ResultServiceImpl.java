package fr.arthb.motherrussia.service.impl;

import fr.arthb.motherrussia.model.Result;
import fr.arthb.motherrussia.repository.ResultRepository;
import fr.arthb.motherrussia.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Override
    public Result getOne(int id) {
        return resultRepository.getOne(id);
    }

    @Override
    public Iterable<Result> findAll() {
        return resultRepository.findAll();
    }

    @Override
    public void save(Result result) {
        resultRepository.save(result);
    }

    @Override
    public void delete(int id) {
        resultRepository.deleteById(id);
    }
}
