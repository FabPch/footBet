package com.yalafoot.bet.service.impl;

import com.yalafoot.bet.exception.CustomException;
import com.yalafoot.bet.model.Gambler;
import com.yalafoot.bet.repository.GamblerRepository;
import com.yalafoot.bet.security.JwtTokenProvider;
import com.yalafoot.bet.service.AuthenticationService;
import com.yalafoot.bet.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private GamblerRepository gamblerRepository;

    @Autowired
    private JwtTokenProvider JwtTokenProvider;

    @Override
    public boolean isAuthenticate(String token) {
        return false;
    }

    @Override
    public String authenticate(String login, String pass) {
        Gambler gambler = gamblerRepository.findByLogin(login);
        String passHash = AppUtils.getPassHashed(pass);
        if (passHash.equalsIgnoreCase(gambler.getPassword())){
            return AppUtils.getUuid();
//            return JwtTokenProvider.createToken(login, gambler.getId());
        } else {
            throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        }
    }
}
