package fr.arthb.motherrussia.service.impl;

import fr.arthb.motherrussia.exception.CustomException;
import fr.arthb.motherrussia.model.Gambler;
import fr.arthb.motherrussia.repository.GamblerRepository;
import fr.arthb.motherrussia.service.GamblerService;
import fr.arthb.motherrussia.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GamblerServiceImpl implements GamblerService {

    @Autowired
    private GamblerRepository gamblerRepository;

    @Override
    public Gambler getOne(int id) {
        return gamblerRepository.getOne(id);
    }

    @Override
    public Iterable<Gambler> findAll() {
        return gamblerRepository.findAll();
    }

    public Iterable<Gambler> findAllOrderByGain() {
        return gamblerRepository.findAllOrderByGain();
    }

    public Iterable<Gambler> findAllSecuredOrderByGain() {
        return gamblerRepository.findAllSecuredOrderByGain();
    }

    @Override
    public void save(Gambler gambler) {
        String pwd = gambler.getPassword();
        String passwordStringHashed = AppUtils.getPassHashed(pwd);
        if (passwordStringHashed != null && !passwordStringHashed.isEmpty()){
            gambler.setPassword(passwordStringHashed);
            gamblerRepository.save(gambler);
        } else {
            throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void delete(int id) {
        gamblerRepository.deleteById(id);
    }
}
