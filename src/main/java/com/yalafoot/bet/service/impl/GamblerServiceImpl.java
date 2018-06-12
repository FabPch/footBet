package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.repository.GamblerRepository;
import com.yalafoot.bet.service.GamblerService;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void save(Gambler gambler) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String pwd = gambler.getPassword();
        byte[] passwordHashed = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
        String passwordStringHashed = new String(Hex.encode(passwordHashed));
        gambler.setPassword(passwordStringHashed);
        gamblerRepository.save(gambler);
    }

    @Override
    public void delete(int id) {
        gamblerRepository.deleteById(id);
    }
}
