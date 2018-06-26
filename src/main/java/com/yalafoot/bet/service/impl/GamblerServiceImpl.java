package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.exception.CustomException;
import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.repository.GamblerRepository;
import com.yalafoot.bet.service.GamblerService;
import com.yalafoot.bet.utils.AppUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
